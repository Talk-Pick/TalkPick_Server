package talkPick.domain.notice.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.adapter.out.repository.NoticeQuerydslRepository;
import talkPick.domain.notice.port.out.NoticeQueryRepositoryPort;
import talkPick.global.response.CursorPageResponse;

@Component
@RequiredArgsConstructor
public class NoticeQueryRepositoryAdapter implements NoticeQueryRepositoryPort {
    private final NoticeQuerydslRepository noticeQuerydslRepository;

    @Override
    public CursorPageResponse<NoticeResDTO.NoticeSummary> findNoticesWithCursor(NoticeReqDTO.Cursor cursor) {
        return null;
    }

    @Override
    public NoticeResDTO.NoticeDetail findNoticeDetailById(Long noticeId) {
        return noticeQuerydslRepository.findNoticeDetailById(noticeId);
    }
}
