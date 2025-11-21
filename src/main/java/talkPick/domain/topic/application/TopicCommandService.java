package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicStatCommandRepositoryPort;
import talkPick.domain.topic.port.in.TopicCommandUseCase;
import talkPick.global.security.annotation.MemberId;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicStatCommandRepositoryPort topicStatCommandRepositoryPort;
    private final TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;

    @Override
    public void addLike(@MemberId Long memberId, Long topicId) {
        topicStatCommandRepositoryPort.incrementLikeCount(topicId);
        topicLikeHistoryCommandRepositoryPort.save(memberId, topicId);
    }
}