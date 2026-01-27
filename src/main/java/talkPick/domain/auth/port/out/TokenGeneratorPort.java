package talkPick.domain.auth.port.out;

import talkPick.domain.auth.adapter.out.dto.TokenResponse;

public interface TokenGeneratorPort {
    TokenResponse.AccessToken generateAccessToken(long memberId, String role);
    TokenResponse.AccessToken generateMasterAccessToken(long memberId, String role);
    String generateRefreshToken();
}