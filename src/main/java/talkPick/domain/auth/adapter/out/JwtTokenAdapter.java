package talkPick.domain.auth.adapter.out;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.handler.JwtExceptionHandler;
import talkPick.core.common.exception.handler.SecurityExceptionHandler;
import talkPick.core.auth.constants.JwtProperties;
import talkPick.domain.auth.port.out.TokenGeneratorPort;
import talkPick.domain.auth.port.out.TokenParserPort;
import talkPick.domain.auth.adapter.out.dto.TokenResponse;

import java.nio.ByteBuffer;
import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static talkPick.core.common.exception.ErrorCode.ROLE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenGeneratorPort, TokenParserPort {
    private final JwtProperties jwtProperties;

    private static final int REFRESH_TOKEN_BYTE_SIZE = 60 * 6 / 8; // 45 Bytes

    // ==================== TokenGeneratorPort ====================

    @Override
    public TokenResponse.AccessToken generateAccessToken(final long memberId, final String role) {
        final var now = LocalDateTime.now();
        final var expireDate = generateExpirationDate(now);

        var accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(memberId))
                .claim("roles", List.of(role))
                .setIssuedAt(convertToDate(now))
                .setExpiration(convertToDate(expireDate))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return TokenResponse.AccessToken.of(memberId, role, accessToken, expireDate);
    }

    @Override
    public TokenResponse.AccessToken generateMasterAccessToken(final long memberId, final String role) {
        final var now = LocalDateTime.now();
        final var expireDate = now.plusYears(100);

        var accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(memberId))
                .claim("roles", List.of(role))
                .setIssuedAt(convertToDate(now))
                .setExpiration(convertToDate(expireDate))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return TokenResponse.AccessToken.of(memberId, role, accessToken, expireDate);
    }

    @Override
    public String generateRefreshToken() {
        var random = createSecureRandom();
        byte[] tokenBytes = new byte[REFRESH_TOKEN_BYTE_SIZE];
        random.nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }

    // ==================== TokenParserPort ====================

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getMemberIdFromToken(String token) {
        try {
            var subject = parseToken(token).getBody().getSubject();
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }

    @Override
    public String getRoleFromToken(String token) {
        List<String> roles = parseToken(token).getBody().get("roles", List.class);

        if (roles == null || roles.isEmpty()) {
            throw new SecurityExceptionHandler(ROLE_NOT_FOUND);
        }

        return roles.getFirst();
    }

    @Override
    public String resolveToken(String bearerHeader) {
        if (bearerHeader != null && bearerHeader.startsWith("Bearer ")) {
            return bearerHeader.substring(7);
        }
        return null;
    }

    // ==================== Private Methods ====================

    private LocalDateTime generateExpirationDate(final LocalDateTime now) {
        return now.plus(jwtProperties.getAccessTokenExpireTime(), java.time.temporal.ChronoUnit.MILLIS);
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(encodeSecretKeyToBase64().getBytes());
    }

    private String encodeSecretKeyToBase64() {
        return Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes());
    }

    private Jws<Claims> parseToken(String token) {
        try {
            var jwtParser = getJwtParser();
            return jwtParser.parseClaimsJws(token.trim());
        } catch (ExpiredJwtException e) {
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new JwtExceptionHandler(ErrorCode.UNSUPPORTED_TOKEN_TYPE);
        } catch (SignatureException e) {
            throw new JwtExceptionHandler(ErrorCode.INVALID_SIGNATURE_TOKEN);
        } catch (Exception e) {
            throw new JwtExceptionHandler(ErrorCode.MALFORMED_TOKEN);
        }
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }

    private SecureRandom createSecureRandom() {
        var buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(System.currentTimeMillis());
        return new SecureRandom(buffer.array());
    }
}