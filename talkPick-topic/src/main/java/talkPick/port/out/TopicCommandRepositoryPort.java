package talkPick.port.out;

public interface TopicCommandRepositoryPort {
    Long addLike(final Long memberId, final Long topicId);;
}