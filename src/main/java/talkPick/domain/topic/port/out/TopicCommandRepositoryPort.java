package talkPick.domain.topic.port.out;

public interface TopicCommandRepositoryPort {
    void addLike(final Long memberId, final Long topicId);
}