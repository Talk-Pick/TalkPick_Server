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
            "/temp-dev-secret-ui/**",
            "/temp-dev-secret-docs/**",
            "/actuator/health/**"
    };
}