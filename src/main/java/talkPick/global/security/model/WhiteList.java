package talkPick.global.security.model;

public final class WhiteList {
    private WhiteList() {} // 인스턴스화 방지

    public static final String[] PATHS = {
            "/api/v1/admin/signup",
            "/api/v1/admin/login",
            "/api/v1/members/email/login",
            "/api/v1/members/kakao/login",
            "/api/v1/members/signup",
            "/api/v1/members/term",
            "/api/v1/members/me",
            "/api/v1/members/liked-topics",
            "/api/v1/members/topic-results",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/test",
            "/actuator/health/**"
    };
}
