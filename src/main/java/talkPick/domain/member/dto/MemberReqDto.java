package talkPick.domain.member.dto;

import lombok.*;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoOAuth2LoginRequest{
        private String idToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshAccessTokenRequest {
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSignupRequest {
        private Long memberId;
        private String nickname;
        private Gender gender;
        private LocalDate birth;
        private String profileImgUrl;
        private MBTI mbti;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TermAgreementRequest {
        private List<Long> agreeTermIdList;
        private List<Long> disagreeTermIdList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MemberEmailReqDto {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MemberMbtiUpdateRequestDto {
        private MBTI mbti;
    }
}
