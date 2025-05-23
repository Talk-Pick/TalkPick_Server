package talkPick.domain.random.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class RandomResDTO {
    public record Categories(
            Long categoryId,
            String categoryGroup,
            String category,
            String imageUrl
    ) {}

    public record RandomTopic(
            Integer order,
            Long topicId,
            String categoryGroup,
            String category,
            String imageUrl,
            String keyword,
            String thumbnail,
            String icon
    ) {}

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
        private List<String> topicImages;

        public void addTopicImage(List<String> topicImages) {
            this.topicImages = topicImages;
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
