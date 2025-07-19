package talkPick.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Cookie 관련 유틸리티 클래스
 * 쿠키 생성, 조회, 삭제 등의 기능을 제공합니다.
 */
@Component
@Slf4j
public class CookieUtil {

    /**
     * 쿠키에서 특정 이름의 값을 추출합니다.
     *
     * @param request HTTP 요청 객체
     * @param name 찾을 쿠키 이름
     * @return 쿠키 값 (없으면 null)
     */
    public String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 쿠키를 생성하고 응답에 추가합니다.
     *
     * @param response HTTP 응답 객체
     * @param name 쿠키 이름
     * @param value 쿠키 값
     * @param path 쿠키 경로
     * @param maxAge 쿠키 유효 시간 (초)
     * @param httpOnly HTTP Only 설정
     * @param sameSite SameSite 설정 (Lax, Strict, None)
     * @param secure Secure 설정
     */
    public void addCookie(HttpServletResponse response, String name, String value, 
                         String path, int maxAge, boolean httpOnly, 
                         String sameSite, boolean secure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        
        if (sameSite != null) {
            cookie.setAttribute("SameSite", sameSite);
        }
        
        cookie.setSecure(secure);
        response.addCookie(cookie);
        
        log.debug("쿠키 추가: {}, 경로: {}, 유효시간: {}초", name, path, maxAge);
    }

    /**
     * 쿠키를 삭제합니다.
     *
     * @param response HTTP 응답 객체
     * @param name 삭제할 쿠키 이름
     * @param path 쿠키 경로
     */
    public void deleteCookie(HttpServletResponse response, String name, String path) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path != null ? path : "/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        
        log.debug("쿠키 삭제: {}, 경로: {}", name, path);
    }
    
    /**
     * 액세스 토큰 쿠키를 추가합니다.
     *
     * @param response HTTP 응답 객체
     * @param accessToken 액세스 토큰
     * @param secure Secure 설정
     */
    public void addAccessTokenCookie(HttpServletResponse response, String accessToken, boolean secure) {
        addCookie(response, "access_token", accessToken, "/", 3600, true, "Lax", secure);
    }
    
    /**
     * 리프레시 토큰 쿠키를 추가합니다.
     *
     * @param response HTTP 응답 객체
     * @param refreshToken 리프레시 토큰
     * @param secure Secure 설정
     */
    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken, boolean secure) {
        addCookie(response, "refresh_token", refreshToken, "/api/v1/auth/refresh", 604800, true, "Strict", secure);
    }
}