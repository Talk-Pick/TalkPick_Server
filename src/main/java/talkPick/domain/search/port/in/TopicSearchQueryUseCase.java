package talkPick.domain.search.port.in;

import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import java.util.List;

public interface TopicSearchQueryUseCase {
    List<TopicSearchResDTO.Topic> getTopics(String category);
    List<TopicSearchResDTO.Topic> search(Long memberId, String word);
    List<TopicSearchResDTO.Recommendation> recommend();
}
