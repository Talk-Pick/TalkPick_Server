package talkPick.domain.search.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TopicSearchResDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Topic {
        private Long topicId;
        private String title;
        private String category;
        private String keyword;
        private long selectCount;
        private long averageTalkTime;
    }

    public record Recommendation (
            Integer order,
            String keyword
    ){}
}