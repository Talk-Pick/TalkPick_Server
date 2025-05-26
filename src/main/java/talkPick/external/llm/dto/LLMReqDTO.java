package talkPick.external.llm.dto;

import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.topic.dto.TopicDataDTO;
import java.util.List;

public record LLMReqDTO(
        RandomTopicHistoryDataDTO historyData,
        MemberDataDTO memberData,
        List<TopicDataDTO> topicsData
) {}