package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.admin.AdminTopicCommandRepositoryPort;

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
