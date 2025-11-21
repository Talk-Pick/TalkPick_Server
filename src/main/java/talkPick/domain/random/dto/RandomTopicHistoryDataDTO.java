package talkPick.domain.random.dto;

import talkPick.domain.random.domain.RandomTopicHistory;

public record RandomTopicHistoryDataDTO(
        Long topicId
) {
    public static RandomTopicHistoryDataDTO from(RandomTopicHistory history) {
        return new RandomTopicHistoryDataDTO(
                history.getId()
        );
    }
}