package talkPick.global.security.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.JwtExceptionHandler;
import talkPick.global.security.jwt.JwtProperties;
import talkPick.global.security.jwt.dto.JwtResDTO;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final JwtProperties jwtProperties;
    private Key key;

    public JwtResDTO.AccessToken generateAccessToken(final long memberId, final String role) {
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

        return JwtResDTO.AccessToken.of(memberId, role, accessToken, expireDate);
    }

    @Profile("!prod")
    public JwtResDTO.AccessToken generateMasterAccessToken(final long memberId, final String role) {
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

        return JwtResDTO.AccessToken.of(memberId, role, accessToken, expireDate);
    }

    private LocalDateTime generateExpirationDate(final LocalDateTime now) {
        return now.plus(jwtProperties.getAccessTokenExpireTime(), java.time.temporal.ChronoUnit.MILLIS);
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(encodeSecretKeyToBase64().getBytes());
    }

    private String encodeSecretKeyToBase64() {
        return Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes());
    }

    public Jws<Claims> parseToken(String token) {
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

    public JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }

    /**
     * 만료 시간 추출
     */
    public LocalDateTime getExpiredAt(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            return LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault());
        } catch (Exception e) {
            throw new JwtExceptionHandler(ErrorCode.INVALID_JWT_TOKEN);
        }
    }
}