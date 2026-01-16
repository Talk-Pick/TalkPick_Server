package talkPick.domain.topic.port.out;

import talkPick.domain.topic.domain.TopicLikeHistory;

public interface TopicLikeHistoryCommandRepositoryPort {
    void save(final Long memberId, final Long topicId);
    void delete(TopicLikeHistory topicLikeHistory);
}
