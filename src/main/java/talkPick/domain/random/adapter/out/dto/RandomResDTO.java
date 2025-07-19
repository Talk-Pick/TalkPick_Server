package talkPick.domain.random.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.topic.domain.type.Keyword;
import talkPick.domain.topic.dto.TopicCacheDTO;

import java.time.LocalDateTime;
import java.util.List;

public class RandomResDTO {
    public record Categories (
            Long categoryId,
            String categoryGroup,
            String category,
            String imageUrl
    ) {}

    public record RandomTopic (
            Integer order,
            Long topicId,
            String categoryGroup,
            String category,
            String imageUrl,
            String keyword,
            String thumbnail,
            String icon
    ) {
        public static RandomTopic of(final Integer orderId, TopicCacheDTO dto) {
            return new RandomTopic(
                    orderId,
                    dto.getId(),
                    dto.getCategoryGroup(),
                    dto.getCategoryTitle(),
                    dto.getCategoryImageUrl(),
                    dto.getKeyword(),
                    dto.getThumbnail(),
                    dto.getIcon()
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomTopicDetail {
        private Long topicId;
        private String title;
        private String detail;
        private String thumbnail;
        private String icon;
        private String category;
        private Keyword keyword;

        private List<String> topicImages;

        public void addTopicImage(List<String> topicImages) {
            this.topicImages = topicImages;
        }

        public RandomTopicDetail(Long topicId, String title, String detail, String thumbnail, String icon, String category, Keyword keyword) {
            this.topicId = topicId;
            this.title = title;
            this.detail = detail;
            this.thumbnail = thumbnail;
            this.icon = icon;
            this.category = category;
            this.keyword = keyword;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Long randomId;
        private List<ResultDetail> details;

        public static Result of(final Long randomId, final List<ResultDetail> details) {
            return Result.builder()
                    .randomId(randomId)
                    .details(details)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultDetail {
        private Long topicId;
        private String title;
        private String category;
        private String keyword;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
    }
}
