package talkPick.domain.today.adapter.out.dto;

import talkPick.domain.topic.domain.type.Keyword;

public class TodayTopicResDTO {
    public record TopicSummaries(
            Long topicId,
            String title,
            long averageTalkTime,
            int selectCount,
            String category,
            Keyword keyword
    ) {
        public String keywordName() {
            return keyword != null ? keyword.name() : null;
        }

        public String keywordIconUrl() {
            return keyword != null ? keyword.getIconUrl() : null;
        }
    }
}