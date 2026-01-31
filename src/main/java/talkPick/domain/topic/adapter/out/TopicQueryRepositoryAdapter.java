package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicQuerydslRepository;
import talkPick.core.common.exception.handler.TopicExceptionHandler;
import java.util.*;

import static talkPick.core.common.exception.ErrorCode.TOPIC_NOT_FOUND;

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
    public List<TopicResDTO.Categories> findCategories() {
        return Optional.ofNullable(topicQuerydslRepository.findCategories())
                .orElse(Collections.emptyList());
    }

    @Override
    public TopicResDTO.TopicDetail findTopicDetail(Long topicId) {
        return Optional.ofNullable(topicQuerydslRepository.findTopicDetailById(topicId))
                .orElseThrow(() -> new TopicExceptionHandler(TOPIC_NOT_FOUND));
    }
}