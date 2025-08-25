package talkPick.domain.topic.adapter.out.dto;

import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.domain.type.Keyword;

public class TopicResDTO {
    public record Topic(
            Long id,
            String title
    ){}

    public record Categories(
            Long categoryId,
            String title,
            String description,
            String imageUrl,
            CategoryGroup categoryGroup
    ) {}

    public record TopicDetail(
            Long topicId,
            String title,
            long averageTalkTime,
            int selectCount,
            String category,
            CategoryGroup categoryGroup,
            String keywordName,
            String keywordImageUrl,
            String keywordIconUrl
    ) {
        public TopicDetail(Long topicId,
                           String title,
                           long averageTalkTime,
                           int selectCount,
                           String category,
                           CategoryGroup categoryGroup,
                           Keyword keyword) {
            this(
                    topicId,
                    title,
                    averageTalkTime,
                    selectCount,
                    category,
                    categoryGroup,
                    keyword != null ? keyword.name() : null,
                    keyword != null ? keyword.getImageUrl() : null,
                    keyword != null ? keyword.getIconUrl() : null
            );
        }
    }
}