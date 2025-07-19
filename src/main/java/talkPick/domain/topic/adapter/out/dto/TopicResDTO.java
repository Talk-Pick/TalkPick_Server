package talkPick.domain.topic.adapter.out.dto;

import talkPick.domain.topic.domain.type.CategoryGroup;
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
            String category,
            CategoryGroup categoryGroup,
            Keyword keyword
    ) {}
    public record TopicDetail(
            Long topicId,
            String title,
            String thumbnail,
            long averageTalkTime,
            int selectCount,
            String category,
            CategoryGroup categoryGroup,
            Keyword keyword
    ) {}
    public record Categories(
            Long categoryId,
            String title,
            String description,
            String imageUrl,
            CategoryGroup categoryGroup
    ) {}
}