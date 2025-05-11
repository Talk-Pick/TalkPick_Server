package talkPick.domain.topic.port.out.admin;

import talkPick.domain.topic.domain.Topic;

public interface AdminTopicCommandRepositoryPort {
    Topic save(final Topic topic);

    void delete(final Long topicId);
}
