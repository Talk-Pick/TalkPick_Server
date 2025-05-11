package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicQuerydslRepository;
import talkPick.global.error.ErrorCode;
import talkPick.exception.topic.TopicNotFoundException;
import talkPick.infra.cache.model.topic.TopicRanking;
import talkPick.global.common.model.PageCustom;

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
    public List<TopicResDTO.TopicDetail> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQuerydslRepository.findTopicDetailsByIds(requestDTO);
    }

    @Override
    public TopicResDTO.TopicDetail findTopicDetail(Long topicId) {
        return topicQuerydslRepository.findTopicDetailById(topicId);
    }
}