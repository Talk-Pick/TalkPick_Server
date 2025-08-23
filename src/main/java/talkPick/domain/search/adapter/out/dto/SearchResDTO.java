package talkPick.domain.search.adapter.out.dto;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
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