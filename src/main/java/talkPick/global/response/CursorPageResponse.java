package talkPick.global.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
public class CursorPageResponse<T> {
    private List<T> items;
    private boolean hasNext;
    private Cursor nextCursor;

    public static class Cursor {
        private LocalDateTime createdAt;
        private Long id;
    }
}