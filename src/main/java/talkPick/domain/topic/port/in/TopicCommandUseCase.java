package talkPick.domain.topic.port.in;

public interface TopicCommandUseCase {
    void toggleLike(Long memberId, Long topicId);
}