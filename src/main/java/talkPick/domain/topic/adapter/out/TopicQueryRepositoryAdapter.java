package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicQuerydslRepository;
import talkPick.global.error.ErrorCode;
import talkPick.domain.topic.exception.TopicNotFoundException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdapter implements TopicQueryRepositoryPort {
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public Topic findTopicById(final Long topicId) {
        return topicJpaRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(ErrorCode.TOPIC_NOT_FOUND));
    }

    @Override
    public Slice<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
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

    @Override
    public List<TopicCacheDTO> findAllTopicCache() {
        return topicQuerydslRepository.findAllTopicData();
    }
}