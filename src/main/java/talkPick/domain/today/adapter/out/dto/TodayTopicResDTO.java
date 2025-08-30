package talkPick.domain.today.adapter.out.dto;

import talkPick.domain.topic.domain.type.Keyword;


public class TodayTopicResDTO {

    public record TopicSummaries(
            Long topicId,
            String title,
            long averageTalkTime,
            int selectCount,
            String category,
            String keywordName,
            String keywordIconUrl
    ) {
        public TopicSummaries(Long topicId,
                              String title,
                              long averageTalkTime,
                              int selectCount,
                              String category,
                              Keyword keyword) {
            this(
                    topicId,
                    title,
                    averageTalkTime,
                    selectCount,
                    category,
                    keyword != null ? keyword.name() : null,
                    keyword != null ? keyword.getIconUrl() : null
            );
        }
    }
}