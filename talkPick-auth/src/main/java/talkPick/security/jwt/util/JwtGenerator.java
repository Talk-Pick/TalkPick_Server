package talkPick.security.jwt.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.config.security.jwt.JwtProperties;
import tictoc.config.security.jwt.dto.JwtResDTO;
import tictoc.error.ErrorCode;
import tictoc.error.exception.UnauthorizedException;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtGenerator {
    private final JwtProperties jwtProperties;

    public JwtResDTO.AccessToken generateAccessToken(final long userId) {
        final var now = LocalDateTime.now();
        final var expireDate = generateExpirationDate(now);

        var accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(convertToDate(now))
                .setExpiration(convertToDate(expireDate))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return JwtResDTO.AccessToken.of(userId, accessToken, expireDate);
    }

    private LocalDateTime generateExpirationDate(final LocalDateTime now) {
        return now.plusMinutes(jwtProperties.getAccessTokenExpireTime());
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
            return jwtParser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException(ErrorCode.UNSUPPORTED_TOKEN_TYPE);
        } catch (SignatureException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_SIGNATURE_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.MALFORMED_TOKEN);
        }
    }

    public JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }
}