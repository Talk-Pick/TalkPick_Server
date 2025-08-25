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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomTopic {
        private Integer order;
        private List<RandomTopicDetail> randomTopicDetails;

        public void addOrder(Integer order) {
            this.order = order;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomTopicDetail {
        private Long topicId;
        private String title;
        private String detail;
        private String categoryGroup;
        private String category;
        private String imageUrl;
        private String keyword;
        private String thumbnail;
        private String icon;

        private List<String> topicImages;
    }
}