package talkPick.domain.search.port.in;

import org.springframework.data.domain.Pageable;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.global.common.model.PageCustom;

public interface SearchQueryUseCase {
    PageCustom<SearchResDTO.Topic> getTopics(String category, Pageable pageable);
    PageCustom<SearchResDTO.Topic> search(String word, Pageable pageable);
}
