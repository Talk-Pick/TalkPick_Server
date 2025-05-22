package talkPick.global.security.jwt.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.global.error.ErrorCode;
import talkPick.global.security.jwt.dto.JwtResDTO;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public JwtResDTO.Login createJwt(final Long userId, final String role) {
        return JwtResDTO.Login.of(
                jwtGenerator.generateAccessToken(userId, role),
                refreshTokenGenerator.generateRefreshToken(userId, role)
        );
    }

    public Long getUserIdFromToken(String token) {
        var subject = jwtGenerator.parseToken(token).getBody().getSubject();
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }

    public String getRoleFromToken(String token) {
        return jwtGenerator.parseToken(token).getBody().get("role", String.class);
    }

    /**
     * JWT 토큰을 쿠키에 추가하는 메소드
     * @param response HTTP 응답 객체
     * @param jwtToken JWT 토큰 정보
     */
    public void addTokenCookies(HttpServletResponse response, JwtResDTO.Login jwtToken) {
        // 액세스 토큰 쿠키
        Cookie accessTokenCookie = new Cookie("access_token", jwtToken.accessToken());
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(3600);
        accessTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(accessTokenCookie);

        // 리프레시 토큰 쿠키
        Cookie refreshTokenCookie = new Cookie("refresh_token", jwtToken.refreshToken());
        refreshTokenCookie.setPath("/api/v1/auth/refresh");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(604800);
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        response.addCookie(refreshTokenCookie);
    }


}