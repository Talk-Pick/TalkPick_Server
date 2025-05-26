package talkPick.domain.admin.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicStatQueryRepositoryPort;
import talkPick.domain.topic.port.in.TopicCommandUseCase;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicStatQueryRepositoryPort topicStatQueryRepositoryPort;
    private final TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;

    @Override
    public void addLike(Long memberId, Long topicId) {
        var findTopicStat = topicStatQueryRepositoryPort.findTopicStatByTopicId(topicId);
        findTopicStat.addLike();
        topicLikeHistoryCommandRepositoryPort.save(memberId, topicId);
    }
}