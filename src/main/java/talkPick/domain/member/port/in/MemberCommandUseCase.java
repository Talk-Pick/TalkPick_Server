package talkPick.domain.member.port.in;

import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;

public interface MemberCommandUseCase {
    Member findOrCreateEmailMember(MemberReqDto.MemberEmailReqDto emailReqDto);
    Member updateMemberMbti(Long memberId, MBTI mbti);
    Member findOrCreateKakaoMember(MemberDataDto.KakaoMemberData kakaoMemberData);
    MemberResDto.MemberSignupResponse memberSignup(String authorization, MemberReqDto.MemberSignupRequest request);
    MemberResDto.TermAgreementResponse termAgreement(String authorization, MemberReqDto.TermAgreementRequest request);
}
