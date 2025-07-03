package talkPick.domain.topic.port.in;

import talkPick.global.security.annotation.UserId;

public interface TopicCommandUseCase {
    void addLike(@UserId Long memberId, Long topicId);
}