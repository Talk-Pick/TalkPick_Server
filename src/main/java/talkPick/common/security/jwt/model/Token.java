package talkPick.common.security.jwt.model;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record Token(
        String accessToken,
        String refreshToken
) {
    public static Token of(final String accessToken, final String refreshToken) {
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}