package talkPick.adapter.out.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.adapter.out.topic.repository.TopicJpaRepository;
import talkPick.adapter.out.topic.repository.TopicQuerydslRepository;
import talkPick.domain.topic.Topic;
import talkPick.common.error.ErrorCode;
import talkPick.exception.topic.TopicNotFoundException;
import talkPick.infra.cache.model.topic.TopicRanking;
import talkPick.common.model.PageCustom;
import talkPick.port.out.topic.TopicQueryRepositoryPort;
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
    public List<TopicResDTO.Categories> findTopCategories() {
        //TODO Redis 저장 고민해야 함.
        return null;
    }

    @Override
    public PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        return topicQuerydslRepository.findCategoriesWithPageable(pageable);
    }

    @Override
    public List<TopicResDTO.TopicSummaries> findTodayTopicSummaries(Long userId) {
        //TODO ADMIN에서 오늘의 토픽 5개를 지정해야 함으로 따로 테이블 팔까 고민 중
        return null;
    }

    @Override
    public List<TopicResDTO.TopicDetails> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQuerydslRepository.findTopicDetailsByIds(requestDTO);
    }
}