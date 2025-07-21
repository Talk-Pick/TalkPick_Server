package talkPick.global.security.jwt.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import talkPick.global.exception.ErrorCode;
import talkPick.global.security.exception.RoleNotFoundException;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.util.CookieUtil;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final CookieUtil cookieUtil;
    
    @Value("${app.secure-cookie:false}")
    private boolean secureCookie;

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
     * JWT 토큰을 쿠키에 추가하는 메소드
     * @param response HTTP 응답 객체
     * @param jwtToken JWT 토큰 정보
     */
    public void addTokenCookies(HttpServletResponse response, JwtResDTO.Login jwtToken) {
        // CookieUtil을 사용하여 토큰 쿠키 추가
        cookieUtil.addAccessTokenCookie(response, jwtToken.accessToken(), secureCookie);
        cookieUtil.addRefreshTokenCookie(response, jwtToken.refreshToken(), secureCookie);
    }
}