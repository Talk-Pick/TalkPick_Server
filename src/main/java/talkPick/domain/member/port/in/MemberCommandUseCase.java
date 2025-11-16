package talkPick.domain.member.port.in;

import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;

public interface MemberCommandUseCase {
//    Member findOrCreateEmailMember(MemberReqDto.MemberEmailRequest emailReqDto);
//    Member loginEmailMember(MemberReqDto.MemberEmailRequest emailReqDto);
    MemberResDto.ProfileUpdateResponse updateProfile(String authorization, MemberReqDto.ProfileUpdateRequest request);
    Member findOrCreateMember(MemberDataDto.MemberData kakaoMemberData, LoginType loginType);
    MemberResDto.MemberSignupResponse memberSignup(String authorization, MemberReqDto.MemberSignupRequest request);
    MemberResDto.TermAgreementResponse termAgreement(String authorization, MemberReqDto.TermAgreementRequest request);
    void logout(String authorization);
    void delete(String authorization);
    void TopicResultCommentChange(String authorization, MemberReqDto.TopicResultCommentChangeRequest request);
}
