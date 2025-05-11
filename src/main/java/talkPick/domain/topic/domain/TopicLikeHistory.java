package talkPick.domain.topic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import talkPick.global.common.model.BaseTime;
import talkPick.global.common.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicLikeHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long topicId;
    private TalkPickStatus status;

    public static TopicLikeHistory of(final Long memberId, final Long topicId) {
        return TopicLikeHistory.builder()
                .memberId(memberId)
                .topicId(topicId)
                .status(TalkPickStatus.ACTIVE)
                .build();
    }
}
