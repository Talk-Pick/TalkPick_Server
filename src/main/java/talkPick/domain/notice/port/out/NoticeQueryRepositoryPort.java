package talkPick.domain.notice.port.out;

import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.global.response.CursorPageResponse;

public interface NoticeQueryRepositoryPort {
    CursorPageResponse<NoticeResDTO.NoticeSummary> findNoticesWithCursor(NoticeReqDTO.Cursor cursor);
    NoticeResDTO.NoticeDetail findNoticeDetailById(Long noticeId);
}
