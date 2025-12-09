package talkPick.global.security.jwt.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class JwtResDTO {
    public record Login(
            Long memberId,
            String role,
            String accessToken,
            Long accessExpiredTime
    ){
        public static Login of(final Long memberId, final String role, final String accessToken, final Long accessExpiredTime) {
            return new Login(
                    memberId,
                    role,
                    accessToken,
                    accessExpiredTime
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

    public record GeneratedTokens(
        String accessToken,
        String refreshToken,
        Long accessExpiredTime,
        Long refreshExpiredTime
    ) {}
}