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
public class TopicLikeHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private TalkPickStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_Id")
    private Topic topic;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }



    public static TopicLikeHistory of(Member member, Topic topic) {
        TopicLikeHistory topicLikeHistory = TopicLikeHistory.builder()
                .status(TalkPickStatus.ACTIVE)
                .build();

        topicLikeHistory.setMember(member);
        topicLikeHistory.setTopic(topic);
        return topicLikeHistory;
    }

}
