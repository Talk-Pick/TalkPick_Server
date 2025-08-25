package talkPick.domain.notice.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.notice.adapter.in.dto.NoticeReqDTO;
import talkPick.domain.notice.adapter.out.dto.NoticeResDTO;
import talkPick.domain.notice.adapter.out.repository.NoticeQuerydslRepository;
import talkPick.domain.notice.port.out.NoticeQueryRepositoryPort;
import talkPick.global.exception.handler.NoticeExceptionHandler;
import talkPick.global.response.CursorPageResponse;
import java.util.Collections;
import java.util.Optional;
import static talkPick.global.exception.ErrorCode.NOTICE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class NoticeQueryRepositoryAdapter implements NoticeQueryRepositoryPort {

    private final NoticeQuerydslRepository noticeQuerydslRepository;

    @Override
    public NoticeResDTO.NoticeDetail findNoticeDetailById(Long noticeId) {
        //TODO 조회수 업데이트 로직 필요

        var result = Optional.ofNullable(noticeQuerydslRepository.findNoticeDetailById(noticeId))
                .orElseThrow(() -> new NoticeExceptionHandler(NOTICE_NOT_FOUND));

        var imageUrls = Optional.ofNullable(noticeQuerydslRepository.findImageUrlsByNoticeId(noticeId))
                .orElse(Collections.emptyList());

        result.addImageUrls(imageUrls);

        return result;
    }

    @Override
    public CursorPageResponse<NoticeResDTO.NoticeSummary> findNoticesWithCursor(NoticeReqDTO.Cursor cursor) {
        return Optional.ofNullable(noticeQuerydslRepository.findNoticesWithCursorRaw(cursor))
                .orElse(CursorPageResponse.<NoticeResDTO.NoticeSummary>builder()
                        .items(Collections.emptyList())
                        .hasNext(false)
                        .nextCursor(null)
                        .build());
    }
}