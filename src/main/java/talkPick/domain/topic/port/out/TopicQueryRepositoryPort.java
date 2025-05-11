package talkPick.domain.topic.port.out;

import org.springframework.data.domain.Pageable;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.Topic;
import talkPick.global.common.model.PageCustom;

import java.util.List;

public interface TopicQueryRepositoryPort {
    Topic findTopicById(final Long topicId);
    List<TopicResDTO.Categories> findTopCategories();
    PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable);
    List<TopicResDTO.TopicSummaries> findTodayTopicSummaries(Long userId);
    List<TopicResDTO.TopicDetail> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
    TopicResDTO.TopicDetail findTopicDetail(Long topicId);
}