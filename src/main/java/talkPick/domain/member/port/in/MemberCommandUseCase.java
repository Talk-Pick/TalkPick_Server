package talkPick.domain.member.port.in;

import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;

public interface MemberCommandUseCase {
    MemberResDto.MemberProfileResponse updateProfile(String authorization, MemberReqDto.ProfileUpdateRequest request);
    Member findOrCreateMember(MemberDataDto.MemberData kakaoMemberData, LoginType loginType);
    Member reactivateMember(MemberDataDto.MemberData memberData, LoginType loginType);
    MemberResDto.MemberSignupResponse memberSignup(String authorization, MemberReqDto.MemberSignupRequest request);
    MemberResDto.TermAgreementResponse termAgreement(String authorization, MemberReqDto.TermAgreementRequest request);
    void logout(String authorization);
    void TopicResultCommentChange(String authorization, MemberReqDto.TopicResultCommentChangeRequest request);
    void delete(String authorization);
}