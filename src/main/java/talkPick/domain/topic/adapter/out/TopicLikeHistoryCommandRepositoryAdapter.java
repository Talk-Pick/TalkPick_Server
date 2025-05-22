package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class TopicLikeHistoryCommandRepositoryAdapter implements TopicLikeHistoryCommandRepositoryPort {
    private final TopicLikeHistoryJpaRepository topicLikeHistoryJpaRepository;

    @Override
    public void save(final Long memberId, final Long topicId) {
        topicLikeHistoryJpaRepository.save(TopicLikeHistory.of(memberId, topicId));
    }
}
