package talkPick.domain.topic.port.out;

import talkPick.domain.topic.domain.TopicLikeHistory;

import java.util.Optional;

public interface TopicLikeHistoryQueryRepositoryPort {
    Optional<TopicLikeHistory> findActiveHistory(final Long memberId, final Long topicId);
}
