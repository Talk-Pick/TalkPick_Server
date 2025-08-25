package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicQuerydslRepository;
import talkPick.global.exception.handler.TopicExceptionHandler;
import java.util.*;
import static talkPick.global.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdapter implements TopicQueryRepositoryPort {
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public Topic findTopicById(final Long topicId) {
        return topicJpaRepository.findById(topicId)
                .orElseThrow(() -> new TopicExceptionHandler(TOPIC_NOT_FOUND));
    }

    @Override
    public List<TopicResDTO.Categories> findCategoriesByCategoryGroup(CategoryGroup categoryGroup) {
        return Optional.ofNullable(topicQuerydslRepository.findCategoriesByCategoryGroup(categoryGroup))
                .orElse(Collections.emptyList());
    }

    @Override
    public TopicResDTO.TopicDetail findTopicDetail(Long topicId) {
        return Optional.ofNullable(topicQuerydslRepository.findTopicDetailById(topicId))
                .orElseThrow(() -> new TopicExceptionHandler(TOPIC_NOT_FOUND));
    }

    @Override
    public List<TopicCacheDTO> findAllTopicCache() {
        return Optional.ofNullable(topicQuerydslRepository.findAllTopicData())
                .orElse(Collections.emptyList());
    }
}