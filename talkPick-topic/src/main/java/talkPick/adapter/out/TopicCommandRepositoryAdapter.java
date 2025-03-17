package talkPick.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.repository.TopicJpaRepository;
import talkPick.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.adapter.out.repository.TopicQuerydslRepository;
import talkPick.common.constants.TopicConstants;
import talkPick.domain.Topic;
import talkPick.domain.like.TopicLikeHistory;
import talkPick.error.ErrorCode;
import talkPick.exception.TopicNotFoundException;
import talkPick.port.out.TopicCommandRepositoryPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicCommandRepositoryAdapter implements TopicCommandRepositoryPort {
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;
    private final TopicLikeHistoryJpaRepository topicLikeHistoryJpaRepository;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Long addLike(final Long memberId, final Long topicId) {
        var key = TopicConstants.LIKE_KEY_PREFIX + topicId;
        try {
            topicLikeHistoryJpaRepository.save(TopicLikeHistory.of(memberId, topicId));
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            log.error("[ERROR] Redis INCR 실패: " + e.getMessage());
            return fallbackLikeCountFromDB(topicId);
        }
    }

    private Long fallbackLikeCountFromDB(Long topicId) {
        return topicJpaRepository.findById(topicId)
                .map(Topic::getLikeCount)
                .orElseThrow(() -> new TopicNotFoundException(ErrorCode.TOPIC_NOT_FOUND));
    }
}