package talkPick.domain.topic.port.in.admin;

import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

public interface AdminTopicCommandUseCase {

    TopicResDTO.Topic createTopicByAdmin(Long adminId, TopicReqDTO.CreateTopic createTopic);

    TopicResDTO.Topic updateTopicByAdmin(Long topicId, TopicReqDTO.CreateTopic updateTopic);

    void deleteTopicByAdmin(Long topicId);
}
