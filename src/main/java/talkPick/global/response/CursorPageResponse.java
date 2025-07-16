package talkPick.global.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CursorPageResponse<T> {
    private List<T> items;
    private boolean hasNext;
    private Cursor nextCursor;

    @Getter
    @AllArgsConstructor
    public static class Cursor {
        private LocalDateTime createdAt;
        private Long id;
    }
}