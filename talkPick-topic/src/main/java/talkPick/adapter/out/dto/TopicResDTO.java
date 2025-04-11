package talkPick.adapter.out.dto;

import talkPick.domain.type.Category;
import talkPick.domain.type.Keyword;

public class TopicResDTO {
    public record Topics(
            Long topicId,
            String title,
            String icon,
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
    public record TopicDetails(
            Long topicId,
            String title,
            String thumbnail,
            long averageTalkTime,
            int selectCount,
            Category category,
            Keyword keyword
    ) {}
}