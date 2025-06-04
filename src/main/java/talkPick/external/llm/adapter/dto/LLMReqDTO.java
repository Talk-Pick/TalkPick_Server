package talkPick.external.llm.adapter.dto;

import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.topic.dto.TopicDataDTO;
import java.util.List;

public record LLMReqDTO(
        List<RandomTopicHistoryDataDTO> historyDatas,
        MemberDataDTO memberData
) {}