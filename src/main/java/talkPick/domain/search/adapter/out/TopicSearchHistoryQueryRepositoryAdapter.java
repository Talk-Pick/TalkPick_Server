package talkPick.domain.search.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.adapter.out.repository.TopicSearchHistoryJpaRepository;
import talkPick.domain.search.port.out.TopicSearchHistoryQueryRepositoryPort;
import java.util.List;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
@Component
@RequiredArgsConstructor
public class TopicSearchHistoryQueryRepositoryAdapter implements TopicSearchHistoryQueryRepositoryPort {
    private final TopicSearchHistoryJpaRepository topicSearchHistoryJpaRepository;

    @Override
    public List<TopicSearchResDTO.Recommendation> recommend() {
        //TODO 검색어를 어떻게 저장하고, 어떻게 보여줄 건가..?
        //TODO 구현해야 함.
        return List.of(
                new TopicSearchResDTO.Recommendation(1, "토스"),
                new TopicSearchResDTO.Recommendation(2, "올인원 금융 관리"),
                new TopicSearchResDTO.Recommendation(3, "밸런스 게임"),
                new TopicSearchResDTO.Recommendation(4, "모바일 간편결제"),
                new TopicSearchResDTO.Recommendation(5, "신용점수"),
                new TopicSearchResDTO.Recommendation(6, "연애")
        );
    }
}