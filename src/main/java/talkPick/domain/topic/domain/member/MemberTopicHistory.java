package talkPick.domain.topic.domain.member;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.domain.type.TopicType;
import talkPick.global.model.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "회원 Topic 기록 테이블")
public class MemberTopicHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '기본 키'")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, columnDefinition = "BIGINT COMMENT '회원 ID'")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false, columnDefinition = "BIGINT COMMENT 'Topic ID'")
    private Topic topic;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_topic_result_id")
    private MemberTopicResult memberTopicResult;


    @Column(name = "talk_time", nullable = false, columnDefinition = "BIGINT COMMENT '토크 시간(ms)'")
    private long talkTime;

    @Column(name = "check_liked", nullable = false, columnDefinition = "BOOLEAN COMMENT '좋아요 여부'")
    private boolean checkLiked;

    @Column(name = "sequence", nullable = false, columnDefinition = "INT COMMENT '진행 순서'")
    private int sequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic_type", nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT 'Topic 유형'")
    private TopicType topicType;

    public void setMember(Member member) {
        this.member = member;
    }
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    public static MemberTopicHistory of(Member member, Topic topic, TopicType topicType,
                                         int sequence, final long talkTime) {
        MemberTopicHistory memberTopicHistory = MemberTopicHistory.builder()
                .talkTime(talkTime)
                .checkLiked(false)
                .topicType(topicType)
                .sequence(sequence)
                .build();

        memberTopicHistory.setMember(member);
        memberTopicHistory.setTopic(topic);
        return memberTopicHistory;
    }
}