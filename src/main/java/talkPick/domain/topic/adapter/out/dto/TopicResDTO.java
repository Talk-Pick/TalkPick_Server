package talkPick.domain.topic.adapter.out.dto;

import talkPick.domain.topic.domain.type.Category;
import talkPick.domain.topic.domain.type.Keyword;

public class TopicResDTO {
    public record Topic(
            Long id,
            String title
    ){}
    public record TopicSummaries(
            Long topicId,
            String title,
            String icon,
            long averageTalkTime,
            int selectCount,
            Category category,
            Keyword keyword
    ) {}
    public record TopicDetails(
            Long topicId,
            String title,
            String thumbnail,
            long averageTalkTime,
            int selectCount,
            Category category,
            Keyword keyword
    ) {}
    public record Categories(
            String category,
            String description,
            String imageUrl
    ) {}
}