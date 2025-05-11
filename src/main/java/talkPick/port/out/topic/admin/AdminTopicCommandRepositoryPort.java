package talkPick.port.out.topic.admin;

import talkPick.domain.topic.Topic;

public interface AdminTopicCommandRepositoryPort {
    Topic save(final Topic topic);

    void delete(final Long topicId);
}
