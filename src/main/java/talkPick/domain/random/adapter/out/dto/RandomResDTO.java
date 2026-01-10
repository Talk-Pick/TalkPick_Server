package talkPick.domain.random.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.topic.domain.Keyword;

import java.util.List;

public class RandomResDTO {
    @Getter
    @AllArgsConstructor
    public static class RandomStart {
        private Long randomId;

        public static RandomStart from(Long randomId) {
            return new RandomStart(randomId);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class RandomTopic {
        private Integer order;
        private List<RandomTopicDetail> randomTopicDetails;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomTopicDetail {
        private Long topicId;
        private String title;
        private String detail;
        private String category;
        private String keywordName;
        private String keywordImageUrl;
        private String keywordIconUrl;
    }
}