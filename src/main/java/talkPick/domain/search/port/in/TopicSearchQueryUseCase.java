package talkPick.domain.search.port.in;

import org.springframework.data.domain.Pageable;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.global.common.model.PageCustom;

import java.util.List;

public interface TopicSearchQueryUseCase {
    List<TopicSearchResDTO.Topic> getTopics(String category, Pageable pageable);
    PageCustom<TopicSearchResDTO.Topic> search(Long memberId, String word, Pageable pageable);
    List<TopicSearchResDTO.Recommendation> recommend();
}
