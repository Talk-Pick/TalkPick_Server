package talkPick.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import talkPick.security.filter.JwtAuthenticationFilter;
import talkPick.security.filter.TokenAuthentication;
import talkPick.security.jwt.JwtProperties;
import talkPick.security.jwt.dto.JwtResDTO;
import talkPick.security.jwt.repository.RefreshTokenRepository;
import talkPick.security.jwt.util.JwtGenerator;
import talkPick.security.jwt.util.JwtProvider;
import talkPick.security.jwt.util.RefreshTokenGenerator;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class JwtIntergrationTest {
    private JwtGenerator jwtGenerator;
    private RefreshTokenGenerator refreshTokenGenerator;
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setAccessTokenExpireTime(3600);
        jwtProperties.setSecret("this-is-talk-pick-jwt-test-secret-key");

        jwtGenerator = new JwtGenerator(jwtProperties);

        RefreshTokenRepository refreshTokenRepository = mock(RefreshTokenRepository.class);
        refreshTokenGenerator = new RefreshTokenGenerator(refreshTokenRepository);

        jwtProvider = new JwtProvider(jwtGenerator, refreshTokenGenerator);
    }

    @Test
    @DisplayName("JWT 토큰 생성 및 파싱 시 Role 클레임 검증")
    void parse_role_from_jwt_token() {
        // given
        long userId = 1L;
        String role = "ADMIN"; // ADMIN role
        JwtResDTO.AccessToken tokenDto = jwtGenerator.generateAccessToken(userId, role);

        // when
        Jws<Claims> jws = jwtGenerator.parseToken(tokenDto.accessToken());
        Claims claims = jws.getBody();

        // then
        assertEquals(role, claims.get("role")); // ADMIN role 파싱 검증
    }

    @Test
    @DisplayName("JwtAuthenticationFilter가 SecurityContext에 인증 객체를 저장하는지 검증")
    void jwt_authentication_filter_sets_authentication() throws ServletException, IOException {
        // given
        long userId = 123L;
        String role = "DEV";
        String token = jwtGenerator.generateAccessToken(userId, role).accessToken();

        var request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        var response = new MockHttpServletResponse();
        var filterChain = mock(FilterChain.class);

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider);

        // when
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // then
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof TokenAuthentication);
        assertEquals(userId, ((TokenAuthentication) authentication).getPrincipal());
        assertEquals(role, ((TokenAuthentication) authentication).getAuthorities().stream().findFirst().get().getAuthority());

    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
