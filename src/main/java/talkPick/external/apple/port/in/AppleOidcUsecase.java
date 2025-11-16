package talkPick.external.apple.port.in;

import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberDataDto;

public interface AppleOidcUsecase {
    MemberDataDto.MemberData verifyAndParseIdToken(MemberReqDto.OAuth2LoginRequest request);
}
