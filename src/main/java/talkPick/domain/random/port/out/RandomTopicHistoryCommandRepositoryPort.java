package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.RandomTopicHistory;

public interface RandomTopicHistoryCommandRepositoryPort {
    RandomTopicHistory saveByCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
    RandomTopicHistory saveByTopic(Long memberId, RandomReqDTO.SelectTopic selectTopic);
}
