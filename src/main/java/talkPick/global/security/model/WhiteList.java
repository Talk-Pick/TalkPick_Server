package talkPick.global.security.model;

public final class WhiteList {
    private WhiteList() {} // 인스턴스화 방지

    public static final String[] PATHS = {
            "/api/v1/admin/signup",
            "/api/v1/admin/login",
            "/api/v1/members/kakao/login",
            "/api/v1/members/apple/login",
            "/api/v1/members/google/login",
            "/api/v1/members/kakao/reactivate",
            "/api/v1/members/google/reactivate",
            "/api/v1/members/apple/reactivate",
            "/api/v1/members/token/refresh",
            "/api/v1/inquiry",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/test",
            "/actuator/health/**"
    };
}
