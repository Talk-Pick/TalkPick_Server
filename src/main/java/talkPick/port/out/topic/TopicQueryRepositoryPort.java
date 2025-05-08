package talkPick.port.out.topic;

import org.springframework.data.domain.Pageable;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.domain.topic.Topic;
import talkPick.common.model.PageCustom;

import java.util.List;

public interface TopicQueryRepositoryPort {
    Topic findTopicById(final Long topicId);
    List<TopicResDTO.Categories> findTopCategories();
    PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable);
    List<TopicResDTO.TopicSummaries> findTodayTopicSummaries(Long userId);
    List<TopicResDTO.TopicDetails> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
}