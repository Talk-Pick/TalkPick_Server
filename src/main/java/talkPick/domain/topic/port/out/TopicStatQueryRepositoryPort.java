package talkPick.domain.topic.port.out;

import talkPick.domain.topic.domain.TopicStat;

public interface TopicStatQueryRepositoryPort {
    TopicStat findTopicStatByTopicId(final Long topicId);
}
