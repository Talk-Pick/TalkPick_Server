package talkPick.global.security.jwt.dto;

import talkPick.global.security.jwt.RefreshToken;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class JwtResDTO {
    public record Login(
            Long memberId,
            String role,
            String accessToken,
            String refreshToken,
            Long accessExpiredTime,
            Long refreshExpiredTime
    ){
        public static Login of(final AccessToken accessToken, final RefreshToken refreshToken) {
            return new Login(
                    refreshToken.getMemberId(),
                    refreshToken.getRole(),
                    accessToken.accessToken,
                    refreshToken.getToken(),
                    accessToken.accessExpiredTime,
                    refreshToken.getExpiredAt().atZone(ZoneId.systemDefault()).toEpochSecond()
            );
        }
    }

    public record AccessToken(
            Long memberId,
            String role,
            String accessToken,
            Long accessExpiredTime
    ) {
        public static AccessToken of(final Long memberId, final String role, final String accessToken, final LocalDateTime expiredAt) {
            return new AccessToken(
                    memberId,
                    role,
                    accessToken,
                    expiredAt.atZone(ZoneId.systemDefault()).toEpochSecond()
            );
        }
    }
}