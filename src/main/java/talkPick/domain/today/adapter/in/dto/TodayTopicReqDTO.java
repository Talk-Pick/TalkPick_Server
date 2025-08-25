package talkPick.domain.today.adapter.in.dto;

import java.util.List;

public class TodayTopicReqDTO {
    public record TodayTopics(
            List<Long> topicIds
    ) {
        public static TodayTopics of(List<Long> topicIds) {
            return new TodayTopics((topicIds));
        }
    }
}
