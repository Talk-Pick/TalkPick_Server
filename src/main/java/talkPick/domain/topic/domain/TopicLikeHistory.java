package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;
import talkPick.global.common.model.BaseTime;
import talkPick.global.common.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "topic_like_history",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"memberId", "topicId"})
        }
)
public class TopicLikeHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private TalkPickStatus status;
    private Long topicId;

    public static TopicLikeHistory of(Long memberId, Long topicId) {
        TopicLikeHistory topicLikeHistory = TopicLikeHistory.builder()
                .status(TalkPickStatus.ACTIVE)
                .memberId(memberId)
                .topicId(topicId)
                .build();
        return topicLikeHistory;
    }
}