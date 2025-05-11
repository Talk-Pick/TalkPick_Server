package talkPick.adapter.out.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.topic.repository.TopicJpaRepository;
import talkPick.domain.topic.Topic;
import talkPick.port.out.topic.admin.AdminTopicCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class AdminTopicCommandRepositoryAdapter implements AdminTopicCommandRepositoryPort {

    private final TopicJpaRepository topicJpaRepository;
    @Override
    public Topic save(Topic topic) {
        return topicJpaRepository.save(topic);
    }

    @Override
    public void delete(Long topicId) {
        topicJpaRepository.deleteById(topicId);
    }
}
