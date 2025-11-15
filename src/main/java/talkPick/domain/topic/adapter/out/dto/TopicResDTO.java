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
            String category,
            CategoryGroup categoryGroup,
            String keywordName,
            String keywordImageUrl
    ) {
        public TopicDetail(Long topicId,
                           String title,
                           String category,
                           CategoryGroup categoryGroup,
                           Keyword keyword) {
            this(
                    topicId,
                    title,
                    category,
                    categoryGroup,
                    keyword != null ? keyword.getName() : null,
                    keyword != null ? keyword.getImageUrl() : null
            );
        }
    }
}