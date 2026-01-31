package talkPick.core.auth.config;

public final class SecurityWhiteList {
    private SecurityWhiteList() {}

    public static final String[] PATHS = {
            "/api/v1/members/kakao/login",
            "/api/v1/members/apple/login",
            "/api/v1/members/google/login",
            "/api/v1/members/kakao/reactivate",
            "/api/v1/members/google/reactivate",
            "/api/v1/members/apple/reactivate",
            "/api/v1/members/token/refresh",
            // Swagger 기본 경로 추가
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/v3/api-docs",
            // 기존 커스텀 경로 (유지)
            "/temp-dev-secret-ui",
            "/temp-dev-secret-ui/**",
            "/temp-dev-secret-docs",
            "/temp-dev-secret-docs/**",
            "/actuator/health/**"
    };
}