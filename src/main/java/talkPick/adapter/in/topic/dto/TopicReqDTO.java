package talkPick.adapter.in.topic.dto;

import talkPick.domain.admin.Admin;
import talkPick.domain.topic.type.Category;
import talkPick.domain.topic.type.Keyword;

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