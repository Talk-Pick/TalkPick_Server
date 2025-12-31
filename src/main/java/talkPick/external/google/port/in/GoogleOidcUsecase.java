package talkPick.external.google.port.in;

import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberDataDto;

public interface GoogleOidcUsecase {
    /**
     * Google ID Token 검증 및 회원 정보 추출
     */
    MemberDataDto.MemberData verifyAndParseIdToken(MemberReqDto.OAuth2LoginRequest request);
}
