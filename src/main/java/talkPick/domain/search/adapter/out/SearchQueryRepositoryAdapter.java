package talkPick.domain.search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.domain.search.adapter.out.repository.SearchQuerydslRepository;
import talkPick.domain.search.port.out.SearchQueryRepositoryPort;
import talkPick.global.common.model.PageCustom;

@Component
@RequiredArgsConstructor
public class SearchQueryRepositoryAdapter implements SearchQueryRepositoryPort {
    private final SearchQuerydslRepository searchQuerydslRepository;

    @Override
    public PageCustom<SearchResDTO.Topic> findTopicsByWordWithPageable(String word, Pageable pageable) {
        return searchQuerydslRepository.findTopicsByWordWithPageable(word, pageable);
    }
}
