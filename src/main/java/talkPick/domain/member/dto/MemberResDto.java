package talkPick.domain.member.dto;

import lombok.*;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.topic.domain.Category;
import talkPick.domain.topic.domain.type.Keyword;
import talkPick.global.model.TalkPickStatus;

import java.time.LocalDateTime;

public class MemberResDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginTokenResponse {
        private String accessToken;
        private String refreshToken;
        private LocalDateTime accessTokenExpireAt;
        private TalkPickStatus talkPickStatus;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TermAgreementResponse {
        private Long memberId;
        private String message;
        private TalkPickStatus talkPickStatus;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshAccessTokenResponse {
        private String accessToken;
        private LocalDateTime accessTokenExpireAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSignupResponse {
        private Long memberId;
        private String nickname;
        private String profileImgUrl;
        private MBTI mbti;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberEmailSignupResponse {
        private Long memberId;
        private String nickname;
        private String profileImgUrl;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileResponse {
        private String nickname;
        private String profileImgUrl;
        private Gender gender;
        private MBTI mbti;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class MemberTopicResultResDto {
        private Long randomId;
        private String comment;
        private LocalDateTime createdDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MemberLikedTopicsResDto {
        private String title;  //토픽 주제 (Topic 테이블)
        private long averageTalkTime; //평균 대화 시간 (Topic 테이블)
        private long selectCount; //선택된 횟수 (Topic 테이블)
        private Keyword keyword; //키워드 (Topickeyword 테이블)
        private Category category; //카테고리 (TopicCategory 테이블)
    }
}
