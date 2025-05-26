package talkPick.domain.search.port.out;

import org.springframework.data.domain.Pageable;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.global.common.model.PageCustom;

public interface TopicSearchQueryRepositoryPort {
    PageCustom<TopicSearchResDTO.Topic> findTopicsByWordWithPageable(String word, Pageable pageable);
}