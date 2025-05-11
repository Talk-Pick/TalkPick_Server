package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.global.error.ErrorCode;
import talkPick.exception.topic.AddLikeFailInRedisException;
import talkPick.domain.topic.port.in.TopicCommandUseCase;
import talkPick.domain.topic.port.out.TopicCommandRepositoryPort;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicCommandService implements TopicCommandUseCase {
    private final TopicCommandRepositoryPort topicCommandRepositoryPort;
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Override
    public void addLike(Long memberId, Long topicId) {
        // TODO: memberId 유효성 체크 → Kafka 적용 후 구현 예정
        try {
            var likeCountInRedis = topicCommandRepositoryPort.addLike(memberId, topicId);
            updateLikeCountEvery10(topicId, likeCountInRedis);
        } catch (Exception e) {
            throw new AddLikeFailInRedisException(ErrorCode.ADD_LIKE_FAIL);
        }
    }

    private void updateLikeCountEvery10(Long topicId, Long likeCountInRedis) {
        if (likeCountInRedis % 10 == 0) {
            var findTopic = topicQueryRepositoryPort.findTopicById(topicId);
            findTopic.addLike(likeCountInRedis);
        }
    }
}