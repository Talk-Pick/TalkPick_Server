package talkPick.port.in.topic.admin;

import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;

public interface AdminTopicCommandUseCase {

    TopicResDTO.Topic createTopicByAdmin(Long adminId, TopicReqDTO.CreateTopic createTopic);

    TopicResDTO.Topic updateTopicByAdmin(Long topicId, TopicReqDTO.CreateTopic updateTopic);

    void deleteTopicByAdmin(Long topicId);
}
