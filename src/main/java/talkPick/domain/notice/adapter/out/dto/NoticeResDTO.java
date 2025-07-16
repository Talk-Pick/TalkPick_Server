package talkPick.domain.notice.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

public class NoticeResDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeSummary {
        private Long noticeId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeDetail {
        private Long noticeId;
        private String title;
        private String content;
        private Integer readCount;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private List<String> imageUrls;

        public NoticeDetail(Long noticeId, String title, String content, Integer readCount, LocalDateTime createdDate, LocalDateTime updatedDate) {
            this.noticeId = noticeId;
            this.title = title;
            this.content = content;
            this.readCount = readCount;
            this.createdDate = createdDate;
            this.updatedDate = updatedDate;
        }

        public void addImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
}
