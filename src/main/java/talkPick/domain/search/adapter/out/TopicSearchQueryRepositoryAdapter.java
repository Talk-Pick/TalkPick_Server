package talkPick.domain.search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.search.adapter.out.repository.TopicSearchQuerydslRepository;
import talkPick.domain.search.port.out.TopicSearchQueryRepositoryPort;

@Component
@RequiredArgsConstructor
public class TopicSearchQueryRepositoryAdapter implements TopicSearchQueryRepositoryPort {
    private final TopicSearchQuerydslRepository topicSearchQuerydslRepository;
}
