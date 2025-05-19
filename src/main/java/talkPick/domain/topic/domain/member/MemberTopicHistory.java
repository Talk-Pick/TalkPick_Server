package talkPick.domain.topic.domain.member;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.member.domain.Member;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.domain.type.TopicType;
import talkPick.global.common.model.BaseTime;
import talkPick.global.common.model.TalkPickStatus;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTopicHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private long talkTime;
    private boolean checkLiked;
    private int sequence;
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