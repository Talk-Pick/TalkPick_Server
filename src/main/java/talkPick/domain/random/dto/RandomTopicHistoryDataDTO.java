package talkPick.domain.random.dto;

import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.topic.domain.type.Keyword;

public record RandomTopicHistoryInfoDTO (
        Long id,
        String title,
        String detail,
        Keyword keyword,
        String category
) {
    public static RandomTopicHistoryInfoDTO from(RandomTopicHistory history) {
        return new RandomTopicHistoryInfoDTO(
                history.getId(),
                history.getTitle(),
                history.getDetail(),
                history.getKeyword(),
                history.getCategory()
        );
    }
}