package talkPick.port.out;

import org.springframework.data.domain.Pageable;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.domain.Topic;
import talkPick.model.PageCustom;

import java.util.List;

public interface TopicQueryRepositoryPort {
    Topic findTopicById(final Long topicId);
    PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable);
    List<TopicResDTO.Topics> findTodayTopics(final Long memberId);
}