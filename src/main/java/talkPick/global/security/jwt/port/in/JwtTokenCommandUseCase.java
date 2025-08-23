package talkPick.global.security.jwt.port.in;

import talkPick.domain.member.domain.Member;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.global.security.jwt.dto.JwtResDTO;

public interface JwtTokenCommandUseCase {
    JwtResDTO.Login generateToken(Member member);
    JwtResDTO.AccessToken refreshAccessToken(MemberReqDto.RefreshAccessTokenRequest request);
}


