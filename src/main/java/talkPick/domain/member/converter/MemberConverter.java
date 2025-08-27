package talkPick.domain.member.converter;

import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.MemberLoginHistory;
import talkPick.domain.member.domain.mapping.MemberTerm;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.term.domain.Term;
import talkPick.global.model.TalkPickStatus;
import java.time.LocalDateTime;

public class MemberConverter {
    public static MemberDataDto.KakaoMemberData toKakaoMemberData(io.jsonwebtoken.Claims claims) {
        return MemberDataDto.KakaoMemberData.builder()
                .sub(claims.getSubject())
                .email(claims.get("email", String.class))
                .build();
    }

    public static MemberResDto.LoginTokenResponse toKakaoOAuth2LoginResponse(
            String accessToken,
            String refreshToken,
            java.time.LocalDateTime accessTokenExpireAt,
            TalkPickStatus talkPickStatus) {
        return MemberResDto.LoginTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireAt(accessTokenExpireAt)
                .talkPickStatus(talkPickStatus)
                .build();
    }
    private static final String DEFAULT_PROFILE_IMG_URL = "https://example.com/images/default-profile.png";
    private static final String DEFAULT_NICKNAME = "토픽";

    public static Member toKakaoMember(MemberDataDto.KakaoMemberData kakaoMemberData) {
        return Member.builder()
                .email(kakaoMemberData.getEmail())
                .memberRole(Role.MEMBER)
                .nickname(DEFAULT_NICKNAME)
                .gender(Gender.NONE)
                .loginType(LoginType.KAKAO)
                .status(TalkPickStatus.PENDING)
                .profileImageUrl(DEFAULT_PROFILE_IMG_URL)
                .providerId(kakaoMemberData.getSub())
                .build();

    }

    public static MemberResDto.ProfileUpdateResponse toProfileUpdateResponse(Member member) {
        return MemberResDto.ProfileUpdateResponse.builder()
                .nickname(member.getNickname())
                .gender(member.getGender() != null ? member.getGender().name().toLowerCase() : null)
                .birth(member.getBirth() != null ? member.getBirth().toString() : null)
                .mbti(member.getMbti() != null ? member.getMbti().toString() : null)
                .message("프로필 수정이 완료되었습니다.")
                .build();
    }

    public static Member toEmailMember(MemberReqDto.MemberEmailReqest emailReqDto){
        return Member.builder()
                .email(emailReqDto.getEmail())
                .password(emailReqDto.getPassword())
                .memberRole(Role.MEMBER)
                .nickname(DEFAULT_NICKNAME)
                .gender(Gender.NONE)
                .loginType(LoginType.EMAIL)
                .status(TalkPickStatus.PENDING)
                .profileImageUrl(DEFAULT_PROFILE_IMG_URL)
                .build();
    }

    public static MemberResDto.MemberSignupResponse toMemberSignupResponse(Member member) {
        return MemberResDto.MemberSignupResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .mbti(member.getMbti())
                .profileImgUrl(member.getProfileImageUrl() != null ? member.getProfileImageUrl() : DEFAULT_PROFILE_IMG_URL)
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

    public static MemberResDto.ProfileResponse toProfileResponse(Member member) {
        return MemberResDto.ProfileResponse.builder()
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfileImageUrl() != null ? member.getProfileImageUrl() : DEFAULT_PROFILE_IMG_URL)
                .mbti(member.getMbti())
                .gender(member.getGender())
                .build();
    }

    public static MemberLoginHistory toLoginHistory(Member member) {
        return MemberLoginHistory.builder()
                .memberId(member.getId())
                .loginTime(LocalDateTime.now())
                .build();
    }

}
