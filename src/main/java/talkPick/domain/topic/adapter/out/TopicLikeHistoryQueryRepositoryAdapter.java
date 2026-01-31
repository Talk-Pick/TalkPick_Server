package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.port.out.TopicLikeHistoryQueryRepositoryPort;
import talkPick.core.common.model.TalkPickStatus;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TopicLikeHistoryQueryRepositoryAdapter implements TopicLikeHistoryQueryRepositoryPort {
    private final TopicLikeHistoryJpaRepository topicLikeHistoryJpaRepository;

    @Override
    public Optional<TopicLikeHistory> findActiveHistory(final Long memberId, final Long topicId) {
        return topicLikeHistoryJpaRepository.findFirstByMemberIdAndTopicIdAndStatusOrderByIdDesc(memberId, topicId, TalkPickStatus.ACTIVE);
    }
}

