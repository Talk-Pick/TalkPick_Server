package talkPick.domain.topic.adapter.in.dto;

import talkPick.domain.topic.domain.type.Category;
import talkPick.domain.topic.domain.type.Keyword;

import java.util.List;

public class TopicReqDTO {

    public record CreateTopic(
            String title,
            String detail,
            String thumbnail,
            String icon,
            Category category,
            Keyword keyword
    ){}

    public record TodayTopics(
            List<Long> topicIds
    ) {}
}