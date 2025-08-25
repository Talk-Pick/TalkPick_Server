package talkPick.domain.topic.port.in;

import talkPick.global.security.annotation.MemberId;

public interface TopicCommandUseCase {
    void addLike(@MemberId Long memberId, Long topicId);
}