package talkPick.port.in.topic.admin;

import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;

import java.util.List;

public interface AdminTopicQueryUseCase {
    List<TopicResDTO.TopicSummaries> getTopic(Long userId);
    List<TopicResDTO.TopicDetails> getDetailTopic(TopicReqDTO.TodayTopics requestDTO);
}
