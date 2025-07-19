package talkPick.domain.search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.adapter.out.repository.TopicSearchHistoryJpaRepository;
import talkPick.domain.search.port.out.TopicSearchHistoryQueryRepositoryPort;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicSearchHistoryQueryRepositoryAdapter implements TopicSearchHistoryQueryRepositoryPort {
    private final TopicSearchHistoryJpaRepository topicSearchHistoryJpaRepository;

    @Override
    public List<TopicSearchResDTO.Recommendation> recommend() {
        //TODO 검색어를 어떻게 저장하고, 어떻게 보여줄 건가..?
        //TODO 구현해야 함.
        return List.of(
                new TopicSearchResDTO.Recommendation(1, "인공지능"),
                new TopicSearchResDTO.Recommendation(2, "자기계발"),
                new TopicSearchResDTO.Recommendation(3, "연애")
        );
    }
}