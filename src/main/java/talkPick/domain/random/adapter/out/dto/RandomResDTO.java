package talkPick.domain.random.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.topic.domain.type.Keyword;
import java.util.List;

public class RandomResDTO {
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
        private String categoryGroup;
        private String category;
        private Keyword keyword;

        public String getKeywordName() {
            return keyword != null ? keyword.name() : null;
        }

        public String getKeywordImageUrl() {
            return keyword != null ? keyword.getImageUrl() : null;
        }

        public String getKeywordIconUrl() {
            return keyword != null ? keyword.getIconUrl() : null;
        }
    }
}