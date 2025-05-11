package talkPick.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "TALK PICK API",
                description = "TALK PICK API 명세서",
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

    //TODO 아래는 예시
//    @Bean
//    public GroupedOpenApi userGroupedOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("User")
//                .pathsToMatch(
//                        "/api/v1/member/oauth/**",
//                        "/api/v1/member/auth/**",
//                        "/api/v1/member/sms/**"
//                )
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi myPageGroupOpenApi() {
//        return GroupedOpenApi.builder()
//                .group("MyPage")
//                .displayName("MyPage")
//                .pathsToMatch("/api/v1/member/my-page/**")
//                .build();
//    }
}
