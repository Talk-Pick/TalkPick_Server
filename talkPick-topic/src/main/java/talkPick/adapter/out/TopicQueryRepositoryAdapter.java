package talkPick.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.adapter.out.repository.TopicJpaRepository;
import talkPick.adapter.out.repository.TopicQuerydslRepository;
import talkPick.domain.Topic;
import talkPick.error.ErrorCode;
import talkPick.exception.TopicNotFoundException;
import talkPick.infrastructure.cache.model.TopicRanking;
import talkPick.model.PageCustom;
import talkPick.port.out.TopicQueryRepositoryPort;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdapter implements TopicQueryRepositoryPort {
    private final RedisTemplate<String, TopicRanking> redisTemplate;
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public Topic findTopicById(final Long topicId) {
        return topicJpaRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(ErrorCode.TOPIC_NOT_FOUND));
    }

    @Override
    public PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        return topicQuerydslRepository.findCategoriesWithPageable(pageable);
    }

    @Override
    public List<TopicResDTO.Topics> findTodayTopics(final Long memberId) {
        String key = "todayTopics:memberId:" + memberId;
        return null;
    }
}