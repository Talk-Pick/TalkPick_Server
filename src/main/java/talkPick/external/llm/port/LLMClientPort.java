package talkPick.external.llm.port;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.topic.dto.TopicCacheDTO;

import java.util.List;

public interface LLMClientPort {
    List<RandomResDTO.RandomTopic> getRandomTopics(List<RandomTopicHistoryDataDTO> randomTopicHistoryData, MemberDataDTO memberData);
    void send(List<TopicCacheDTO> topicCaches);
}
