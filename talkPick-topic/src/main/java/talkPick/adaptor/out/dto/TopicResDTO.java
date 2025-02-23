package talkPick.adaptor.out.dto;

import talkPick.domain.type.Category;
import talkPick.domain.type.Keyword;

public class TopicResDTO {
    public record Topics(
            Long topicId,
            String content,
            long averageTalkTime,
            int selectCount,
            Category category,
            Keyword keyword
    ) {}
    public record Categories(
            String category,
            String description
    ) {}
}