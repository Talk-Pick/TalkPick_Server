package talkPick.domain.topic.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.Topic;
import java.util.List;

public interface TopicQueryRepositoryPort {
    Topic findTopicById(final Long topicId);
    Slice<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable);
    List<TopicResDTO.TopicSummaries> findTodayTopicSummaries(Long userId);
    List<TopicResDTO.TopicDetail> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
    TopicResDTO.TopicDetail findTopicDetail(Long topicId);
    List<TopicCacheDTO> findAllTopicCache();
}