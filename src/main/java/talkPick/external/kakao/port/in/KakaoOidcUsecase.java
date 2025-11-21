package talkPick.external.kakao.port.in;

import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;

public interface KakaoOidcUsecase {
    MemberDataDto.MemberData verifyAndParseIdToken(MemberReqDto.OAuth2LoginRequest request);
}
