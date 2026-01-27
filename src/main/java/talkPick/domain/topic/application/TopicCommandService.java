package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.domain.event.TopicLikedEvent;
import talkPick.domain.topic.port.in.TopicCommandUseCase;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicLikeHistoryQueryRepositoryPort;
import talkPick.global.model.TalkPickStatus;
import talkPick.global.security.resolver.MemberId;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;
    private final TopicLikeHistoryQueryRepositoryPort topicLikeHistoryQueryRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void toggleLike(Long memberId, Long topicId) {
        Optional<TopicLikeHistory> activeHistory = topicLikeHistoryQueryRepositoryPort
                .findActiveHistory(memberId, topicId);

        if (activeHistory.isPresent()) {
            topicLikeHistoryCommandRepositoryPort.delete(activeHistory.get());
        } else {
            topicLikeHistoryCommandRepositoryPort.save(memberId, topicId);
            eventPublisher.publishEvent(TopicLikedEvent.of(this, memberId, topicId));
        }
    }
}