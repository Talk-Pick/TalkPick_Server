package talkPick.port.out.topic;

public interface TopicCommandRepositoryPort {
    Long addLike(final Long memberId, final Long topicId);;
}