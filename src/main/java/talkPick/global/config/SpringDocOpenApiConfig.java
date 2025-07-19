package talkPick.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

// http://localhost:8080/swagger-ui/index.html
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "TalkPick",
                description = "TalkPick API 명세서",
                version = "1.0.0")
)
@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }

//    @Bean
//    public GroupedOpenApi memberOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("Member")
//                .displayName("Member")
//                .pathsToMatch("/api/v1/member/my-page/**")
//                .build();
//    }

    @Bean
    public GroupedOpenApi topicOpenApi() {
        return GroupedOpenApi.builder()
                .group("톡픽 API")
                .displayName("톡픽 API")
                .pathsToMatch("/api/v1/topic/**")
                .build();
    }

    @Bean
    public GroupedOpenApi searchOpenApi() {
        return GroupedOpenApi.builder()
                .group("톡픽 검색 API")
                .displayName("톡픽 검색 API")
                .pathsToMatch("/api/v1/search/**")
                .build();
    }

    @Bean
    public GroupedOpenApi randomOpenApi() {
        return GroupedOpenApi.builder()
                .group("랜덤 대화 코스 API")
                .displayName("랜덤 대화 코스 API")
                .pathsToMatch("/api/v1/random/**")
                .build();
    }

    @Bean
    public GroupedOpenApi signupOpenApi() {
        return GroupedOpenApi.builder()
                .group("회원가입 API")
                .displayName("회원가입 API")
                .pathsToMatch(
                    "/api/v1/auth/kakao/authorize",
                    "/api/v1/auth/kakao/callback",
                    "/api/v1/auth/kakao/additional",
                    "/api/v1/members/join"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi memberCommandOpenApi() {
        return GroupedOpenApi.builder()
                .group("회원 명령 API")
                .displayName("회원 명령 API")
                .pathsToMatch(
                    "/api/v1/members/mbti",
                    "/api/v1/topic/additional"
                )
                .build();
    }
}
