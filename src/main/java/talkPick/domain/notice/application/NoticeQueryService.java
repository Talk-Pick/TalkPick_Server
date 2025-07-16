package talkPick.domain.notice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.port.in.NoticeQueryUseCase;
import talkPick.domain.notice.port.out.NoticeQueryRepositoryPort;
import talkPick.global.response.CursorPageResponse;

@Service
@RequiredArgsConstructor
public class NoticeQueryService implements NoticeQueryUseCase {
    private final NoticeQueryRepositoryPort noticeQueryRepositoryPort;

    @Override
    public CursorPageResponse<NoticeResDTO.NoticeSummary> getNotices(NoticeReqDTO.Cursor cursor) {
        return noticeQueryRepositoryPort.findNoticesWithCursor(cursor);
    }

    @Override
    public NoticeResDTO.NoticeDetail getNoticeDetail(Long noticeId) {
        return noticeQueryRepositoryPort.findNoticeDetailById(noticeId);
    }
}
