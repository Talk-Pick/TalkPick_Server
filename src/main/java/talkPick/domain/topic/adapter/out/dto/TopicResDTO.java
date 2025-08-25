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
            Keyword keyword
    ) {
        public String keywordName() {
            return keyword != null ? keyword.name() : null;
        }

        public String keywordImageUrl() {
            return keyword != null ? keyword.getImageUrl() : null;
        }

        public String keywordIconUrl() {
            return keyword != null ? keyword.getIconUrl() : null;
        }
    }
}