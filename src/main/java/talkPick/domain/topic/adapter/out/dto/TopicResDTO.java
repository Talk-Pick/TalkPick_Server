package talkPick.domain.topic.adapter.out.dto;

import talkPick.domain.topic.domain.Keyword;
import talkPick.domain.topic.domain.type.CategoryGroup;

public class TopicResDTO {
    public record Topic(
            Long id,
            String title
    ){}

    public record Categories(
            Long categoryId,
            String title,
            String imageUrl,
            CategoryGroup categoryGroup
    ) {}

    public record TopicDetail(
            Long topicId,
            String title,
            String detail,
            String category,
            CategoryGroup categoryGroup,
            String keywordName,
            String keywordImageUrl,
            String topicImageUrl
    ) {}
}