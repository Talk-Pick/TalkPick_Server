package talkPick.domain.search.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.domain.search.port.in.SearchQueryUseCase;
import talkPick.domain.search.port.out.SearchQueryRepositoryPort;
import talkPick.domain.topic.port.out.TopicDataCacheManagerPort;
import talkPick.global.common.model.PageCustom;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchQueryService implements SearchQueryUseCase {
    private final SearchQueryRepositoryPort searchQueryRepositoryPort;
    private final TopicDataCacheManagerPort topicDataCacheManagerPort;

    @Override
    public List<SearchResDTO.Topic> getTopics(String category, Pageable pageable) {
        var topicDataList = topicDataCacheManagerPort.getAll();
        return topicDataList.stream()
                .filter(t -> t.getCategoryTitle().equals(category))
                .map(t -> new SearchResDTO.Topic(
                        t.getId(),
                        t.getTitle(),
                        t.getCategoryTitle(),
                        t.getKeyword(),
                        t.getSelectCount(),
                        t.getAverageTalkTime()
                ))
                .toList();
    }

    @Override
    public PageCustom<SearchResDTO.Topic> search(String word, Pageable pageable) {
        return searchQueryRepositoryPort.findTopicsByWordWithPageable(word, pageable);
    }
}
