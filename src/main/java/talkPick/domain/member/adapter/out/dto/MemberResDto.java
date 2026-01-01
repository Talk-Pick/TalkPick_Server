package talkPick.domain.member.adapter.out.dto;

import lombok.*;
import talkPick.domain.member.domain.type.Gender;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.topic.domain.Category;
import talkPick.domain.topic.domain.Keyword;
import talkPick.global.model.TalkPickStatus;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TermAgreementResponse {
        private Long memberId;
        private String message;
        private TalkPickStatus talkPickStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSignupResponse {
        private String nickname;
        private MBTI mbti;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberProfileResponse {
        private String nickname;
        private MBTI mbti;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberTopicResultResDto {
        private Long id; // 히스토리 ID (MemberTopicHistory)
        private String comment; // 한 줄 코멘트
        private List<String> topicKeyword; // 토픽 키워드
        private LocalDateTime createdDate; // 대화 날짜
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberLikedTopicResDto {
        private Long topicId; // 좋아요 누른 토픽 id (Topic 테이블)
        private String title;  //토픽 주제 (Topic 테이블)
        private String keyword; //키워드 (Topickeyword 테이블)
        private Category category; //카테고리 (TopicCategory 테이블)
        private LocalDateTime createdDate; // 좋아요 누른 시간
    }
}
