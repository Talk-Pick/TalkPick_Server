package talkPick.domain.today.adapter.out.dto;

import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.domain.type.Keyword;

public class TodayTopicResDTO {
    public record TopicSummaries(
            Long topicId,
            String title,
            String icon,
            long averageTalkTime,
            int selectCount,
            String category,
            Keyword keyword
    ) {}
//    public record TopicDetail(
//            Long topicId,
//            String title,
//            String detail,
//            String thumbnail,
//            long averageTalkTime,
//            int selectCount,
//            String category,
//            CategoryGroup categoryGroup,
//            Keyword keyword
//    ) {}
}
