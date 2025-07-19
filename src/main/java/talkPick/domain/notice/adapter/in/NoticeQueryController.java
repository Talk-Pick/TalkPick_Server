package talkPick.domain.notice.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.port.in.NoticeQueryUseCase;
import talkPick.global.response.CursorPageResponse;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class NoticeQueryController implements NoticeQueryApi {
    private final NoticeQueryUseCase noticeQueryUseCase;

    @Override
    public CursorPageResponse<NoticeResDTO.NoticeSummary> getNotices(LocalDateTime cursorCreatedAt, Long cursorId, int size) {
        return noticeQueryUseCase.getNotices(new NoticeReqDTO.Cursor(cursorCreatedAt, cursorId, size));
    }

    @Override
    public NoticeResDTO.NoticeDetail getNoticeDetail(Long noticeId) {
        return noticeQueryUseCase.getNoticeDetail(noticeId);
    }
}
