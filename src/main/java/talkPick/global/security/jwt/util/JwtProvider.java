package talkPick.global.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.JwtHandler;
import talkPick.global.security.exception.RoleNotFoundException;
import talkPick.global.security.jwt.dto.JwtResDTO;

import java.security.Key;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private Key key;


    public JwtResDTO.Login createJwt(final Long memberId, final String role) {
        return JwtResDTO.Login.of(
                jwtGenerator.generateAccessToken(memberId, role),
                refreshTokenGenerator.generateRefreshToken(memberId, role)
        );
    }

    public Long getMemberIdFromToken(String token) {
        try {
            var subject = jwtGenerator.parseToken(token).getBody().getSubject();
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }

    public String getRoleFromToken(String token) {
        List<String> roles = jwtGenerator.parseToken(token).getBody().get("roles", List.class);

        if (roles == null || roles.isEmpty()) {
            throw new RoleNotFoundException(ErrorCode.ROLE_NOT_FOUND);
        }

        return roles.getFirst();
    }

    /**
     * JWT 유효성 검증 (true/false)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtHandler(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (Exception e) {
            throw new JwtHandler(ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    /**
     * Authorization 헤더에서 실제 토큰 추출
     */
    public String resolveToken(String bearerHeader) {
        if (bearerHeader != null && bearerHeader.startsWith("Bearer ")) {
            return bearerHeader.substring(7);
        }
        return null;
    }

    public Long getMemberId(String bearerHeader) {
        // 1. Bearer 헤더에서 실제 토큰 값 추출
        String token = resolveToken(bearerHeader);
        if (token == null) {
            throw new JwtHandler(ErrorCode.UNAUTHORIZED);   // 토큰이 없으면 인증 실패로 처리
        }

        // 2. 토큰 유효성 검증
        validateToken(token);

        // 3. JWT에서 사용자 ID 추출
        try {
            // 추출
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            throw new JwtHandler(ErrorCode.INVALID_JWT_TOKEN);
        }
    }
}