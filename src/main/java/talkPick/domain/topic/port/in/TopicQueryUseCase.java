package talkPick.domain.topic.port.in;

import org.springframework.data.domain.Pageable;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.common.model.PageCustom;
import java.util.List;

public interface TopicQueryUseCase {
    List<TopicResDTO.Categories> getTopCategories();
    PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable);
    List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId);
    List<TopicResDTO.TopicDetails> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
}