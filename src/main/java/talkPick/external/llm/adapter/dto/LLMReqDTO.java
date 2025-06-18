package talkPick.external.llm.adapter.dto;

import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import java.util.List;

public record LLMReqDTO(
        List<RandomTopicHistoryDataDTO> randomTopicHistoryData,
        MemberDataDTO memberData
) {}