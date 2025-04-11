package talkPick.port.out;

import org.springframework.data.domain.Pageable;
import talkPick.adapter.in.dto.TopicReqDTO;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.domain.Topic;
import talkPick.model.PageCustom;

import java.util.List;

public interface TopicQueryRepositoryPort {
    Topic findTopicById(final Long topicId);
    List<TopicResDTO.Categories> findTopCategories();
    PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable);
    List<TopicResDTO.TopicSummaries> findTodayTopicSummaries();
    List<TopicResDTO.TopicDetails> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
}