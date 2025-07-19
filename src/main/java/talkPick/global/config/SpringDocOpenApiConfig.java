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
    public GroupedOpenApi noticeOpenApi() {
        return GroupedOpenApi.builder()
                .group("공지사항 API")
                .displayName("공지사항 API")
                .pathsToMatch("/api/v1/notices/**")
                .build();
    }
}
