package talkPick.domain.search.port.in;

import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import java.util.List;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
public interface TopicSearchQueryUseCase {
    List<TopicSearchResDTO.Topic> getTopics(String category);
    List<TopicSearchResDTO.Topic> search(Long memberId, String word);
    List<TopicSearchResDTO.Recommendation> recommend();
}
