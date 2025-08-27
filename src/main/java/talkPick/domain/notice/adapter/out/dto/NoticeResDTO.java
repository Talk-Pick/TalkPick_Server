package talkPick.domain.notice.adapter.out.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "공지사항 ID", example = "100")
        private Long noticeId;
        @Schema(description = "공지사항 제목", example = "서비스 점검 안내")
        private String title;
        @Schema(description = "공지사항 내용", example = "2025년 8월 30일 서비스 점검 안내드립니다.")
        private String content;
        @Schema(description = "공지사항 작성 일시", example = "2025-08-26T12:34:56")
        private LocalDateTime createdAt;
        @Schema(description = "공지사항 수정 일시", example = "2025-08-26T15:00:00")
        private LocalDateTime updatedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeDetail {
        @Schema(description = "공지사항 ID", example = "100")
        private Long noticeId;
        @Schema(description = "공지사항 제목", example = "서비스 점검 안내")
        private String title;
        @Schema(description = "공지사항 내용", example = "2025년 8월 30일 서비스 점검 안내드립니다.")
        private String content;
        @Schema(description = "공지사항 조회 수", example = "123")
        private Integer readCount;
        @Schema(description = "공지사항 작성 일시", example = "2025-08-26T12:34:56")
        private LocalDateTime createdDate;
        @Schema(description = "공지사항 수정 일시", example = "2025-08-26T15:00:00")
        private LocalDateTime updatedDate;
        @Schema(description = "공지사항에 포함된 이미지 URL 목록", example = "[\"https://example.com/image1.png\", \"https://example.com/image2.png\"]")
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
