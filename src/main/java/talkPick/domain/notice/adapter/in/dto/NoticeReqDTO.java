package talkPick.domain.notice.adapter.in.dto;

import java.time.LocalDateTime;

public class NoticeReqDTO {
    public record Cursor (
            LocalDateTime cursorCreatedAt,
            Long cursorId,
            int size
    ) {}
}
