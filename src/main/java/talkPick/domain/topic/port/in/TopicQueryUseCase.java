package talkPick.domain.topic.port.in;

import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import java.util.List;

public interface TopicQueryUseCase {
    List<TopicResDTO.Categories> getCategories();
    TopicResDTO.TopicDetail getTopicDetail(Long topicId);
}