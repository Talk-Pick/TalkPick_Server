package talkPick.domain.topic.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.core.common.model.BaseTime;
import talkPick.core.common.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "topic_like_history"
)
public class TopicLikeHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(name = "member_id", nullable = false, columnDefinition = "BIGINT COMMENT '회원 ID'")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '좋아요 상태'")
    private TalkPickStatus status;

    @Column(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    public static TopicLikeHistory of(Long memberId, Long topicId) {
        return TopicLikeHistory.builder()
                .status(TalkPickStatus.ACTIVE)
                .memberId(memberId)
                .topicId(topicId)
                .build();
    }

    public void delete() {
        this.status = TalkPickStatus.DIS_ACTIVE;
    }
}