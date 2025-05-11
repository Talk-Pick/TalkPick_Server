package talkPick.global.security.jwt.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.global.error.ErrorCode;
import talkPick.global.security.jwt.dto.JwtResDTO;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;

    public JwtResDTO.Login createJwt(final Long userId, final String role) {
        return JwtResDTO.Login.of(
                jwtGenerator.generateAccessToken(userId, role),
                refreshTokenGenerator.generateRefreshToken(userId, role)
        );
    }

    public Long getUserIdFromToken(String token) {
        var subject = jwtGenerator.parseToken(token).getBody().getSubject();
        try {
            return Long.parseLong(subject);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.valueOf(ErrorCode.TOKEN_SUBJECT_NOT_NUMERIC_STRING));
        }
    }

    public String getRoleFromToken(String token) {
        return jwtGenerator.parseToken(token).getBody().get("role", String.class);
    }
}