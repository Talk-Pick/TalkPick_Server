package talkPick.domain.topic.adapter.in.dto;

import talkPick.domain.topic.domain.type.Keyword;
import java.util.List;

public class TopicReqDTO {
    public record Create(
            String title,
            String detail,
            String thumbnail,
            String icon,
            String category,
            Keyword keyword
    ){}

    public record TodayTopics(
            List<Long> topicIds
    ) {}
}