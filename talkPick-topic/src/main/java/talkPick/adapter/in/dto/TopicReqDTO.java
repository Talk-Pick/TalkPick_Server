package talkPick.adapter.in.dto;

import java.util.List;

public class TopicReqDTO {
    public record TodayTopics(
            List<Long> topicIds
    ) {}
}