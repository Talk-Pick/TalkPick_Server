package talkPick.domain.topic.adapter.out.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import talkPick.domain.topic.domain.event.TopicLikedEvent;
import talkPick.domain.topic.port.out.TopicStatCommandRepositoryPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicLikedEventHandler {
    private final TopicStatCommandRepositoryPort topicStatCommandRepositoryPort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(TopicLikedEvent event) {
        try {
            topicStatCommandRepositoryPort.incrementLikeCount(event.getTopicId());
        } catch (Exception e) {
            log.error("토픽 좋아요 수 증가 실패 - topicId: {}", event.getTopicId(), e);
        }
    }
}