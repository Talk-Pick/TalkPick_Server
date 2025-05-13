package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicStatQueryRepositoryPort;
import talkPick.global.error.ErrorCode;
import talkPick.domain.topic.exception.AddLikeFailInRedisException;
import talkPick.domain.topic.port.in.TopicCommandUseCase;
import talkPick.domain.topic.port.out.TopicStatCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicStatQueryRepositoryPort topicStatQueryRepositoryPort;
    private final TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;

    @Override
    public void addLike(Long memberId, Long topicId) {
        try {
            var findTopicStat = topicStatQueryRepositoryPort.findTopicStatByTopicId(topicId);
            findTopicStat.addLike();
            topicLikeHistoryCommandRepositoryPort.save(memberId, topicId);
        } catch (Exception e) {
            throw new AddLikeFailInRedisException(ErrorCode.ADD_LIKE_FAIL);
        }
    }
}