package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.adapter.out.repository.TopicStatJpaRepository;
import talkPick.domain.topic.domain.TopicStat;
import talkPick.domain.topic.port.out.TopicStatCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class TopicStatCommandRepositoryAdapter implements TopicStatCommandRepositoryPort {
    private final TopicStatJpaRepository topicStatJpaRepository;

    @Override
    public void save(Long topicId) {
        topicStatJpaRepository.save(TopicStat.of(topicId));
    }
}