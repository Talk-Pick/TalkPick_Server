package talkPick.domain.notice.port.in;

import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.global.response.CursorPageResponse;

public interface NoticeQueryUseCase {
    CursorPageResponse<NoticeResDTO.NoticeSummary> getNotices(NoticeReqDTO.Cursor cursor);
    NoticeResDTO.NoticeDetail getNoticeDetail(Long noticeId);
}