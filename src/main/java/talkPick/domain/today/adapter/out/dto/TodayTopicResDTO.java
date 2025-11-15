package talkPick.domain.today.adapter.out.dto;

public class TodayTopicResDTO {

    public record TopicSummaries(
            Long topicId,
            String title,
            long averageTalkTime,
            int selectCount,
            String category,
            String keywordName,
            String keywordIconUrl
    ) {}
}