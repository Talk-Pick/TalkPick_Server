package talkPick.common.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "refresh_token", timeToLive = 3600 * 24 * 7)
public class RefreshToken {
    @Id
    private String token;
    private Long userId;
    private String role;
    private LocalDateTime expiredAt;

    public static RefreshToken of(final String token, final Long userId, final String role, LocalDateTime expiredAt) {
        return RefreshToken.builder()
                .token(token)
                .userId(userId)
                .role(role)
                .expiredAt(expiredAt)
                .build();
    }
}
