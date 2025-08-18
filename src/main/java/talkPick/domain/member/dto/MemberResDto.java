package talkPick.domain.member.dto;

import lombok.*;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.topic.domain.Category;
import talkPick.domain.topic.domain.type.Keyword;
import talkPick.global.model.TalkPickStatus;

import java.time.LocalDateTime;
import java.util.List;

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
    public static class SignupResponse {
        private Long memberId;
        private String email;
        private String message;
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
        private String comment; // 한 줄 코멘트
        private List<Keyword> topicKeyword; // 토픽 키워드
        private LocalDateTime createdDate; // 대화 날짜
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MemberLikedTopicResDto {
        private Long id; // 좋아요 누른 토픽 id (TopicLikeHistory 테이블)
        private String title;  //토픽 주제 (Topic 테이블)
        private long averageTalkTime; //평균 대화 시간 (Topic 테이블)
        private long selectCount; //선택된 횟수 (Topic 테이블)
        private Keyword keyword; //키워드 (Topickeyword 테이블)
        private Category category; //카테고리 (TopicCategory 테이블)
        private LocalDateTime createdDate; // 좋아요 누른 시간
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProfileUpdateResponse {
        private String nickname;
        private String gender;
        private String birth;
        private String message;
        private String mbti;
    }
}
