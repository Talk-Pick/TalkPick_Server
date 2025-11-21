package talkPick.domain.notice.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class NoticeReqDTO {
    public record Cursor (
            @Schema(description = "마지막으로 조회된 공지사항 작성 일시", example = "2025-08-26T12:34:56")
            LocalDateTime cursorCreatedAt,
            @Schema(description = "마지막으로 조회된 공지사항 ID", example = "100")
            Long cursorId,
            @Schema(description = "한 번에 조회할 공지사항 개수", example = "20")
            int size
    ) {}
}