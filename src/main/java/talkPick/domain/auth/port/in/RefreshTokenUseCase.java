package talkPick.domain.auth.port.in;

import talkPick.domain.auth.adapter.out.dto.TokenResponse;

public interface RefreshTokenUseCase {
    TokenResponse.AccessToken refreshAccessToken(String refreshToken);
}