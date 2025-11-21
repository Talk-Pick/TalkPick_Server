package talkPick.domain.today.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import talkPick.domain.today.domain.TodayTopic;
import java.util.List;

@Getter
public class TodayTopicSavedEvent extends ApplicationEvent {
    private final List<TodayTopic> todayTopics;

    private TodayTopicSavedEvent(Object source, List<TodayTopic> todayTopics) {
        super(source);
        this.todayTopics = todayTopics;
    }

    public static TodayTopicSavedEvent of(Object source, List<TodayTopic> todayTopics) {
        return new TodayTopicSavedEvent(source, todayTopics);
    }
}