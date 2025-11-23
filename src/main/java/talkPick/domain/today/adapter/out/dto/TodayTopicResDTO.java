package talkPick.domain.today.adapter.out.dto;

public class TodayTopicResDTO {

    public record TodayTopic(
            Long topicId,
            String title,
            String category,
            String keywordName,
            String keywordIconUrl
    ) {}
}