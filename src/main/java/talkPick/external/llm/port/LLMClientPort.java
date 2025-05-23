package talkPick.external.llm.port;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.random.dto.TopicDataDTO;

import java.util.List;

public interface LLMClientPort {
    List<RandomResDTO.RandomTopic>random(RandomTopicHistoryDataDTO randomTopicHistoryData, MemberDataDTO memberData, List<TopicDataDTO> topicData);
}
