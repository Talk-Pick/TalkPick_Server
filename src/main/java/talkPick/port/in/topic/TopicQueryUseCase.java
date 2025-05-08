package talkPick.port.in.topic;

import org.springframework.data.domain.Pageable;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.common.model.PageCustom;
import java.util.List;

public interface TopicQueryUseCase {
    List<TopicResDTO.Categories> getTopCategories();
    PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable);
    List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId);
    List<TopicResDTO.TopicDetails> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
}