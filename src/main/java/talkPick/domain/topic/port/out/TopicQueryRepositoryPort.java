package talkPick.domain.topic.port.out;

import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.Topic;
import java.util.List;

public interface TopicQueryRepositoryPort {
    Topic findTopicById(final Long topicId);
    List<TopicResDTO.Categories> findCategoriesByCategoryGroup(CategoryGroup categoryGroup);
    TopicResDTO.TopicDetail findTopicDetail(Long topicId);
}