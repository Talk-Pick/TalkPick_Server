package talkPick.core.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "TalkPick",
                description = "TalkPick API 명세서",
                version = "1.0.0")
)
@Configuration
public class SpringDocConfig {

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

    @Bean
    public GroupedOpenApi topicOpenApi() {
        return GroupedOpenApi.builder()
                .group("톡픽 API")
                .displayName("톡픽 API")
                .pathsToMatch("/api/v1/topic/**")
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
    public GroupedOpenApi noticeOpenApi() {
        return GroupedOpenApi.builder()
                .group("공지사항 API")
                .displayName("공지사항 API")
                .pathsToMatch("/api/v1/notices/**")
                .build();
    }

    @Bean
    public GroupedOpenApi signupOpenApi() {
        return GroupedOpenApi.builder()
                .group("사용자 API")
                .displayName("사용자 API")
                .pathsToMatch("/api/v1/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi inquiryOpenApi() {
        return GroupedOpenApi.builder()
                .group("문의 API")
                .displayName("문의 API")
                .pathsToMatch("/api/v1/inquiry/**")
                .build();
    }
}
