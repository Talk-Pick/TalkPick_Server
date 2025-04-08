package talkPick.security.jwt.dto;

import talkPick.security.jwt.RefreshToken;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class JwtResDTO {
    public record Login(
            Long userId,
            String role,
            String accessToken,
            String refreshToken,
            Long accessExpiredTime,
            Long refreshExpiredTime
    ){
        public static Login of(final AccessToken accessToken, final RefreshToken refreshToken) {
            return new Login(
                    refreshToken.getUserId(),
                    refreshToken.getRole(),
                    accessToken.accessToken,
                    refreshToken.getToken(),
                    accessToken.accessExpiredTime,
                    refreshToken.getExpiredAt().atZone(ZoneId.systemDefault()).toEpochSecond()
            );
        }
    }

    public record AccessToken(
            Long userId,
            String role,
            String accessToken,
            Long accessExpiredTime
    ) {
        public static AccessToken of(final Long userId, final String role, final String accessToken, final LocalDateTime expiredAt) {
            return new AccessToken(
                    userId,
                    role,
                    accessToken,
                    expiredAt.atZone(ZoneId.systemDefault()).toEpochSecond()
            );
        }
    }
}