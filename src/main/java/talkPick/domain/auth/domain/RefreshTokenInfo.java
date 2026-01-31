package talkPick.domain.auth.domain;

import java.time.LocalDateTime;

public record RefreshTokenInfo(
        Long id,
        Long memberId,
        String token,
        LocalDateTime expiredAt
) {
    public static RefreshTokenInfo of(final Long memberId, final String token, final LocalDateTime expiredAt) {
        return new RefreshTokenInfo(null, memberId, token, expiredAt);
    }

    public static RefreshTokenInfo withId(final Long id, final Long memberId, final String token, final LocalDateTime expiredAt) {
        return new RefreshTokenInfo(id, memberId, token, expiredAt);
    }

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public RefreshTokenInfo updateToken(final String newToken, final LocalDateTime newExpiredAt) {
        return new RefreshTokenInfo(this.id, this.memberId, newToken, newExpiredAt);
    }
}