package talkPick.domain.search.port.out;

import org.springframework.data.domain.Pageable;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.global.common.model.PageCustom;

public interface SearchQueryRepositoryPort {
    PageCustom<SearchResDTO.Topic> findTopicsByCategoryWithPageable(String category, Pageable pageable);
    PageCustom<SearchResDTO.Topic> findTopicsByWordWithPageable(String word, Pageable pageable);
}