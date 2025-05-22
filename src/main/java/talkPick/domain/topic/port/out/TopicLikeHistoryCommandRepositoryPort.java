package talkPick.domain.topic.port.out;

public interface TopicLikeHistoryCommandRepositoryPort {
    void save(final Long memberId, final Long topicId);
}
