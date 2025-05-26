package talkPick.domain.admin.port.in;

import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

import java.util.List;

public interface AdminTopicQueryUseCase {
    List<TopicResDTO.TopicSummaries> getTopic(Long userId);
    List<TopicResDTO.TopicDetail> getDetailTopic(TopicReqDTO.TodayTopics requestDTO);
}
