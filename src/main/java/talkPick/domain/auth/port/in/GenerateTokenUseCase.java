package talkPick.domain.auth.port.in;

import talkPick.domain.auth.adapter.out.dto.TokenResponse;
import talkPick.domain.member.domain.Member;

public interface GenerateTokenUseCase {
    TokenResponse.GeneratedTokens generateToken(Member member);
}