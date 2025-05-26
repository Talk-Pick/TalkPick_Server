package talkPick.domain.search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.adapter.out.repository.TopicSearchQuerydslRepository;
import talkPick.domain.search.port.out.TopicSearchQueryRepositoryPort;
import talkPick.global.common.model.PageCustom;

@Component
@RequiredArgsConstructor
public class TopicSearchQueryRepositoryAdapter implements TopicSearchQueryRepositoryPort {
    private final TopicSearchQuerydslRepository topicSearchQuerydslRepository;

    @Override
    public PageCustom<TopicSearchResDTO.Topic> findTopicsByWordWithPageable(String word, Pageable pageable) {
        return topicSearchQuerydslRepository.findTopicsByWordWithPageable(word, pageable);
    }
}
