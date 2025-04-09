package talkPick.application;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import talkPick.security.jwt.JwtProperties;
import talkPick.security.jwt.dto.JwtResDTO;
import talkPick.security.jwt.util.JwtGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtGeneratorTest {

    private JwtGenerator jwtGenerator;

    @BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setAccessTokenExpireTime(3600);
        jwtProperties.setSecret("this-is-talk-pick-jwt-test-secret-key");

        jwtGenerator = new JwtGenerator(jwtProperties);
    }

    @Test
    @DisplayName("JWT 토큰 생성 및 파싱 시 Role 클레임 검증")
    void parse_role_from_jwt_token() {
        // given
        long userId = 1L;
        String role = "USER";
        JwtResDTO.AccessToken tokenDto = jwtGenerator.generateAccessToken(userId, role);

        // when
        Jws<Claims> claims = jwtGenerator.parseToken(tokenDto.accessToken());

        // then
        assertEquals(role, claims.getBody().get("role"));
    }
}
