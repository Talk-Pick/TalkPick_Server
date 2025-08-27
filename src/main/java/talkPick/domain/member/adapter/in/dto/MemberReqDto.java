package talkPick.domain.member.adapter.in.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        @NotNull(message = "idToken 은 필수입니다.")
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
        @NotEmpty(message = "닉네임은 필수입니다.")
        @Size(max = 25, message = "닉네임은 최대 25자입니다.")
        private String nickname;
        @NotNull(message = "성별은 필수입니다.")
        private Gender gender;
        @NotNull(message = "생년월일은 필수입니다.")
        private LocalDate birth;
        @NotNull(message = "프로필 이미지는 필수입니다.")
        private String profileImgUrl;
        @NotNull(message = "mbti는 필수입니다.")
        private MBTI mbti;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileUpdateRequest {
        @NotNull(message = "닉네임은 필수입니다.")
        @Size(max = 25, message = "닉네임은 최대 25자입니다.")
        private String nickname;
        @NotNull(message = "성별은 필수입니다.")
        private Gender gender;
        @NotNull(message = "생년월일은 필수입니다.")
        private LocalDate birth;
        @NotNull(message = "MBTI는 필수입니다.")
        private MBTI mbti;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TermAgreementRequest {
        @NotNull(message = "동의 약관 목록은 null일 수 없습니다.")
        private List<Long> agreeTermIdList;
        @NotNull(message = "비동의 약관 목록은 null일 수 없습니다. 빈 배열로 넘겨주세요.")
        private List<Long> disagreeTermIdList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MemberEmailReqest {
        @NotNull(message = "이메일은 필수입니다.")
        private String email;
        @NotNull(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopicResultCommentChangeRequest {
        @NotNull(message = "히스토리 ID는 필수입니다.")
        private Long memberTopicHistoryId;
        @Size(max = 100, message = "코멘트는 최대 100자입니다.")
        private String comment;
    }
}
