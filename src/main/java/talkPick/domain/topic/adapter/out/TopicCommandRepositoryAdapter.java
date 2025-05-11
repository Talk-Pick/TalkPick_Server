package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.port.out.TopicCommandRepositoryPort;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.global.common.constants.topic.TopicConstants;
import talkPick.global.error.ErrorCode;
import talkPick.exception.topic.TopicNotFoundException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicCommandRepositoryAdapter implements TopicCommandRepositoryPort {
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