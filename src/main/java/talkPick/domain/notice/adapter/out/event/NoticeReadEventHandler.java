package talkPick.domain.notice.adapter.out.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import talkPick.domain.notice.adapter.out.repository.NoticeJpaRepository;
import talkPick.domain.notice.domain.Notice;
import talkPick.domain.notice.domain.event.NoticeReadEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeReadEventHandler {
    private final NoticeJpaRepository noticeJpaRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(NoticeReadEvent event) {
        try {
            noticeJpaRepository.findById(event.getNoticeId())
                    .ifPresent(Notice::plusReadCount);
        } catch (Exception e) {
            log.error("공지사항 조회수 증가 실패 - noticeId: {}", event.getNoticeId(), e);
        }
    }
}