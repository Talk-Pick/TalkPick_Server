package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.exception.DuplicateLikeException;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import talkPick.global.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class TopicLikeHistoryCommandRepositoryAdapter implements TopicLikeHistoryCommandRepositoryPort {
    private final TopicLikeHistoryJpaRepository topicLikeHistoryJpaRepository;

    @Override
    public void save(final Long memberId, final Long topicId) {
        try {
            topicLikeHistoryJpaRepository.save(TopicLikeHistory.of(memberId, topicId));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLikeException(ErrorCode.DUPLICATE_LIKE);
        }
    }
}

