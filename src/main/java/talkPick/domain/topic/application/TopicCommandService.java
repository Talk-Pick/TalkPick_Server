package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.domain.event.TopicLikedEvent;
import talkPick.domain.topic.port.in.TopicCommandUseCase;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.global.security.annotation.MemberId;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void addLike(@MemberId Long memberId, Long topicId) {
        topicLikeHistoryCommandRepositoryPort.save(memberId, topicId);
        eventPublisher.publishEvent(TopicLikedEvent.of(this, memberId, topicId));
    }
}