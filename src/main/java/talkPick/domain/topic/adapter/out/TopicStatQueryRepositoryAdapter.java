package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicStatJpaRepository;
import talkPick.domain.topic.domain.TopicStat;
import talkPick.domain.topic.port.out.TopicStatQueryRepositoryPort;
import talkPick.core.common.exception.handler.TopicExceptionHandler;

import static talkPick.core.common.exception.ErrorCode.TOPIC_STAT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TopicStatQueryRepositoryAdapter implements TopicStatQueryRepositoryPort {
    private final TopicStatJpaRepository topicStatJpaRepository;

    @Override
    public TopicStat findTopicStatByTopicId(final Long topicId) {
        return topicStatJpaRepository.findByTopicId(topicId)
                .orElseThrow(() -> new TopicExceptionHandler(TOPIC_STAT_NOT_FOUND));
    }
}