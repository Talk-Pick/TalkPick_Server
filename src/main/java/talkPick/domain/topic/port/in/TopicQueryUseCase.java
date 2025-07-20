package talkPick.domain.topic.port.in;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

public interface TopicQueryUseCase {
    Slice<TopicResDTO.Categories> getCategories(Pageable pageable);
    TopicResDTO.TopicDetail getTopicDetail(Long topicId);
}