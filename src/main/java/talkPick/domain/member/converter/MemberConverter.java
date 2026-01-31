package talkPick.domain.member.converter;

import io.jsonwebtoken.Claims;
import talkPick.domain.member.domain.type.Role;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.MemberLoginHistory;
import talkPick.domain.member.domain.mapping.MemberTerm;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.term.domain.Term;
import talkPick.core.common.model.TalkPickStatus;
import java.time.LocalDateTime;

public class MemberConverter {
    private static final String DEFAULT_NICKNAME = "토픽";

    public static MemberDataDto.MemberData toKakaoMemberData(io.jsonwebtoken.Claims claims) {
        return MemberDataDto.MemberData.builder()
                .sub(claims.getSubject())
                .email(claims.get("email", String.class))
                .build();
    }

    public static MemberDataDto.MemberData toAppleMemberData(Claims claims) {
        return MemberDataDto.MemberData.builder()
                .sub(claims.getSubject())
                .email(claims.get("email", String.class) != null ? claims.get("email", String.class) : "NONE")
                .build();
    }

    public static MemberDataDto.MemberData toGoogleMemberData(io.jsonwebtoken.Claims claims) {
        return MemberDataDto.MemberData.builder()
                .sub(claims.getSubject())
                .email(claims.get("email", String.class))
                .build();
    }

    public static Member toMember(MemberDataDto.MemberData MemberData, LoginType loginType) {
        return Member.builder()
                .email(MemberData.getEmail())
                .memberRole(Role.MEMBER)
                .nickname(DEFAULT_NICKNAME)
                .loginType(loginType)
                .status(TalkPickStatus.PENDING)
                .providerId(MemberData.getSub())
                .build();

    }

    public static MemberResDto.MemberProfileResponse toMemberProfileResponse(Member member) {
        return MemberResDto.MemberProfileResponse.builder()
                .nickname(member.getNickname())
                .mbti(member.getMbti())
                .build();
    }

    public static MemberResDto.MemberSignupResponse toMemberSignupResponse(Member member) {
        return MemberResDto.MemberSignupResponse.builder()
                .nickname(member.getNickname())
                .mbti(member.getMbti())
                .build();
    }

    public static MemberTerm toMemberTerm(Member member, Term term, Boolean isAgree) {
        return MemberTerm.builder()
                .memberId(member.getId())
                .termId(term.getId())
                .isAgree(isAgree)
                .build();
    }

    public static MemberResDto.TermAgreementResponse toTermAgreementResponse(Member member) {
        return MemberResDto.TermAgreementResponse.builder()
                .memberId(member.getId())
                .message("약관 동의가 완료되었습니다.")
                .talkPickStatus(TalkPickStatus.AGREE)
                .build();
    }

    public static MemberLoginHistory toLoginHistory(Member member) {
        return MemberLoginHistory.builder()
                .memberId(member.getId())
                .loginTime(LocalDateTime.now())
                .build();
    }
}