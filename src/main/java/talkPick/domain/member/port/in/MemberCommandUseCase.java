package talkPick.domain.member.port.in;

import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;

public interface MemberCommandUseCase {
    Member findOrCreateEmailMember(MemberReqDto.MemberEmailReqDto emailReqDto);
    Member loginEmailMember(MemberReqDto.MemberEmailReqDto emailReqDto);
    MemberResDto.ProfileUpdateResponse updateProfile(String authorization, MemberReqDto.ProfileUpdateRequest request);
    Member findOrCreateKakaoMember(MemberDataDto.KakaoMemberData kakaoMemberData);
    MemberResDto.MemberSignupResponse memberSignup(String authorization, MemberReqDto.MemberSignupRequest request);
    MemberResDto.TermAgreementResponse termAgreement(String authorization, MemberReqDto.TermAgreementRequest request);
    void logout(String authorization);
    void delete(String authorization);
}
