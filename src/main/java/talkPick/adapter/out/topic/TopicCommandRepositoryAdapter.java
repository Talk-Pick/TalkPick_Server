package talkPick.adapter.out.topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.topic.repository.TopicJpaRepository;
import talkPick.adapter.out.topic.repository.TopicLikeHistoryJpaRepository;
import talkPick.common.constants.topic.TopicConstants;
import talkPick.domain.topic.Topic;
import talkPick.domain.topic.like.TopicLikeHistory;
import talkPick.common.error.ErrorCode;
import talkPick.exception.topic.TopicNotFoundException;
import talkPick.port.out.topic.TopicCommandRepositoryPort;

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