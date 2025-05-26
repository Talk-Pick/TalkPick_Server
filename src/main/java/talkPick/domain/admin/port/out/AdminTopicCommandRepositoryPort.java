package talkPick.domain.admin.port.out;

import talkPick.domain.topic.domain.Topic;

public interface AdminTopicCommandRepositoryPort {
    Topic save(final Topic topic);

    void delete(final Long topicId);
}
