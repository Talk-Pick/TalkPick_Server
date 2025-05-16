package talkPick.domain.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.domain.search.port.in.SearchQueryUseCase;
import talkPick.domain.search.port.out.SearchQueryRepositoryPort;
import talkPick.global.common.model.PageCustom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchQueryService implements SearchQueryUseCase {
    private final SearchQueryRepositoryPort searchQueryRepositoryPort;

    @Override
    public PageCustom<SearchResDTO.Topic> getTopics(String category, Pageable pageable) {
        return searchQueryRepositoryPort.findTopicsByCategoryWithPageable(category, pageable);
    }

    @Override
    public PageCustom<SearchResDTO.Topic> search(String word, Pageable pageable) {
        return searchQueryRepositoryPort.findTopicsByWordWithPageable(word, pageable);
    }
}
