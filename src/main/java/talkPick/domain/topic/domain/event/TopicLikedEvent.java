package talkPick.domain.topic.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TopicLikedEvent extends ApplicationEvent {
    private final Long memberId;
    private final Long topicId;

    private TopicLikedEvent(Object source, Long memberId, Long topicId) {
        super(source);
        this.memberId = memberId;
        this.topicId = topicId;
    }

    public static TopicLikedEvent of(Object source, Long memberId, Long topicId) {
        return new TopicLikedEvent(source, memberId, topicId);
    }
}
