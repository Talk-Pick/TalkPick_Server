package talkPick.domain.random.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class RandomResDTO {
    public record Categories(
            String category,
            String imageUrl
    ) {}

    public record RandomTopic(
            Integer order,
            Long topicId,
            String category,
            String imageUrl,
            String keyword,
            String thumbnail,
            String icon
    ) {}

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class RandomTopicDetail {
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
}
