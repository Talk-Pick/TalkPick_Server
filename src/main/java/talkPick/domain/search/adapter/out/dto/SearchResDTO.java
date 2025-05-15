package talkPick.domain.search.adapter.out.dto;

public class SearchResDTO {
    public class Topic {
        private Long topicId;
        private String title;
        private String category;
        private String keyword;
        private long selectCount;
        private long averageTalkTime;
    }
}