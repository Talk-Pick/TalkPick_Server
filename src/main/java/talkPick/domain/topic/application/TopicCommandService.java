package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.exception.DuplicateLikeException;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicStatQueryRepositoryPort;
import talkPick.domain.topic.port.in.TopicCommandUseCase;
import talkPick.global.exception.ErrorCode;
import talkPick.global.security.annotation.UserId;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicStatQueryRepositoryPort topicStatQueryRepositoryPort;
    private final TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;

    @Override
    public void addLike(@UserId Long memberId, Long topicId) {
        var findTopicStat = topicStatQueryRepositoryPort.findTopicStatByTopicId(topicId);
        findTopicStat.addLike();
        try {
            topicLikeHistoryCommandRepositoryPort.save(memberId, topicId);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLikeException(ErrorCode.DUPLICATE_LIKE);
        }
    }
}