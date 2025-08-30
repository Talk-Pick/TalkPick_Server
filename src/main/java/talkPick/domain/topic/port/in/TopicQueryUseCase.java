package talkPick.domain.topic.port.in;

import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.type.CategoryGroup;
import java.util.List;

public interface TopicQueryUseCase {
    List<TopicResDTO.Categories> getCategories(CategoryGroup categoryGroup);
    TopicResDTO.TopicDetail getTopicDetail(Long topicId);
}