package talkPick.domain.topic.adapter.out.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.domain.event.TopicLikedEvent;
import talkPick.domain.topic.port.out.TopicStatCommandRepositoryPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicLikedEventHandler {
    private final TopicStatCommandRepositoryPort topicStatCommandRepositoryPort;

    @Async
    @EventListener
    @Transactional
    public void handle(TopicLikedEvent event) {
        try {
            topicStatCommandRepositoryPort.incrementLikeCount(event.getTopicId());
        } catch (Exception e) {
            log.error("토픽 좋아요 수 증가 실패 - topicId: {}", event.getTopicId(), e);
        }
    }
}