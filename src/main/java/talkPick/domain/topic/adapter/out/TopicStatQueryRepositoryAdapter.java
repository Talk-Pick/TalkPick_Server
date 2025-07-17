package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicStatJpaRepository;
import talkPick.domain.random.exception.TopicStatNotFoundException;
import talkPick.domain.topic.domain.TopicStat;
import talkPick.domain.topic.port.out.TopicStatQueryRepositoryPort;

import static talkPick.global.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class TopicStatQueryRepositoryAdapter implements TopicStatQueryRepositoryPort {
    private final TopicStatJpaRepository topicStatJpaRepository;

    @Override
    public TopicStat findTopicStatByTopicId(final Long topicId) {
        return topicStatJpaRepository.findByTopicId(topicId).orElseThrow(() -> new TopicStatNotFoundException(TOPIC_STAT_NOT_FOUND));
    }
}
