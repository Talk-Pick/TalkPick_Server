package talkPick.domain.topic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.global.error.ErrorCode;
import talkPick.domain.topic.exception.AddLikeFailInRedisException;
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
        try {
            topicCommandRepositoryPort.addLike(memberId, topicId);
        } catch (Exception e) {
            throw new AddLikeFailInRedisException(ErrorCode.ADD_LIKE_FAIL);
        }
    }
}