package talkPick.domain.topic.domain.member;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.topic.domain.type.TopicType;
import talkPick.global.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_topic_history")
public class MemberTopicHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_topic_history_id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @Column(
            name = "member_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT '회원 PK (Foreign Key)'"
    )
    private Long memberId;

    @Column(name = "topic_id",
            nullable = false,
            columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Long topicId;

    @Column(name = "member_topic_result_id",
            columnDefinition = "BIGINT COMMENT 'MemberTopicResult ID'")
    private Long member_topic_result_id;

    @Column(name = "talk_time", nullable = false, columnDefinition = "BIGINT COMMENT '토크 시간(ms)'")
    private long talkTime;

    @Column(name = "check_liked", nullable = false, columnDefinition = "BOOLEAN COMMENT '좋아요 여부'")
    private boolean checkLiked;

    @Column(name = "sequence", nullable = false, columnDefinition = "INT COMMENT '진행 순서'")
    private int sequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic_type", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT 'Topic 유형'")
    private TopicType topicType;

    public void setMember(Long memberId) {
        this.memberId = memberId;
    }
    public void setTopic(Long topicId) {
        this.topicId = topicId;
    }
    public static MemberTopicHistory of(Long memberId, Long topicId, TopicType topicType,
                                         int sequence, final long talkTime) {
        MemberTopicHistory memberTopicHistory = MemberTopicHistory.builder()
                .talkTime(talkTime)
                .checkLiked(false)
                .topicType(topicType)
                .sequence(sequence)
                .build();

        memberTopicHistory.setMember(memberId);
        memberTopicHistory.setTopic(topicId);
        return memberTopicHistory;
    }
}