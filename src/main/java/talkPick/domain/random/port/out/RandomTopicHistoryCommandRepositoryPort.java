package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.RandomTopicHistory;

public interface RandomTopicHistoryCommandRepositoryPort {
    RandomTopicHistory selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
    RandomTopicHistory selectTopic(Long memberId, RandomReqDTO.SelectTopic selectTopic);
}
