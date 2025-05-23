package talkPick.domain.random.dto;

import talkPick.domain.random.domain.RandomTopicHistory;

public record RandomTopicHistoryDataDTO(
        Long id,
        String title,
        String detail,
        String keyword,
        String categoryGroup,
        String category
) {
    public static RandomTopicHistoryDataDTO from(RandomTopicHistory history) {
        return new RandomTopicHistoryDataDTO(
                history.getId(),
                history.getTitle(),
                history.getDetail(),
                history.getKeyword().toString(),
                history.getCategoryGroup().toString(),
                history.getCategory()
        );
    }
}