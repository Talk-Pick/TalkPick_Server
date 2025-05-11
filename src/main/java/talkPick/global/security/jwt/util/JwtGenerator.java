package talkPick.global.security.jwt.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.auth.UnauthorizedException;
import talkPick.global.security.jwt.JwtProperties;
import talkPick.global.security.jwt.dto.JwtResDTO;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final JwtProperties jwtProperties;

    public JwtResDTO.AccessToken generateAccessToken(final long userId, final String role) {
        final var now = LocalDateTime.now();
        final var expireDate = generateExpirationDate(now);

        var accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setIssuedAt(convertToDate(now))
                .setExpiration(convertToDate(expireDate))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return JwtResDTO.AccessToken.of(userId, role, accessToken, expireDate);
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
        } catch (Exception e) {
            log.warn("Expired JWT token: {}", token);
            throw new UnauthorizedException(ErrorCode.MALFORMED_TOKEN);
        }
    }

    public JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }
}