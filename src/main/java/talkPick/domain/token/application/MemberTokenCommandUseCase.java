package talkPick.domain.token.application;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;

public interface MemberTokenCommandUseCase {
    MemberResDto.LoginTokenResponse generateToken(Member member);
    MemberResDto.RefreshAccessTokenResponse refreshAccessToken(MemberReqDto.RefreshAccessTokenRequest request);
}
