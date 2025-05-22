package talkPick.domain.topic.port.in;

public interface TopicCommandUseCase {
    void addLike(Long memberId, Long topicId);
}