package talkPick.domain.search.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.port.in.TopicSearchQueryUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicSearchController implements TopicSearchApi {
    private final TopicSearchQueryUseCase topicSearchQueryUseCase;

    @Override
    public List<TopicSearchResDTO.Topic> getTopics(String category) {
        return topicSearchQueryUseCase.getTopics(category);
    }

    @Override
    public List<TopicSearchResDTO.Topic> search(Long memberId, String word) {
        return topicSearchQueryUseCase.search(memberId, word);
    }

    @Override
    public List<TopicSearchResDTO.Recommendation> recommend() {
        return topicSearchQueryUseCase.recommend();
    }
}