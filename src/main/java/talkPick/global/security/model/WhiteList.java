package talkPick.global.security.model;

public final class WhiteList {
    private WhiteList() {} // 인스턴스화 방지

    public static final String[] PATHS = {
            "/api/v1/admin/signup",
            "/api/v1/admin/login",
            "/api/v1/member/login",
            "/api/v1/oauth/kakao/authorize",
            "/api/v1/topic/kakao",
            "/api/v1/topic/additional",
            "/api/v1/topic",
            "/mbti-form.html",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/test"
    };
}
