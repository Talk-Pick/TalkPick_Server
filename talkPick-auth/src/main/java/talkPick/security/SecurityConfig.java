package talkPick.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tictoc.config.CorsFilter;
import tictoc.config.security.exception.ExceptionHandlerFilter;
import tictoc.config.security.exception.JwtAuthenticationEntryPoint;
import tictoc.config.security.filter.JwtAuthenticationFilter;
import tictoc.config.security.jwt.util.JwtProvider;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CorsFilter corsFilter;
//        private final CorsConfig corsConfig;
    private static final String[] whiteList = {
            "/api/v1/member/login",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())) // CORS 설정 추가
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(whiteList).permitAll()
                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .anyRequest()
                                        .authenticated())
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) // CorsFilter 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(whiteList);
    }
}
