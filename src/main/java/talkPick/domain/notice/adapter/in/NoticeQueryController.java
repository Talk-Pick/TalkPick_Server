package talkPick.domain.notice.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.port.in.NoticeQueryUseCase;
import talkPick.global.response.CursorPageResponse;

@RestController
@RequiredArgsConstructor
public class NoticeQueryController implements NoticeQueryApi {
    private final NoticeQueryUseCase noticeQueryUseCase;

    @Override
    public CursorPageResponse<NoticeResDTO.NoticeSummary> getNotices(NoticeReqDTO.Cursor cursor) {
        return noticeQueryUseCase.getNotices(cursor);
    }

    @Override
    public NoticeResDTO.NoticeDetail getNoticeDetail(Long noticeId) {
        return noticeQueryUseCase.getNoticeDetail(noticeId);
    }
}
