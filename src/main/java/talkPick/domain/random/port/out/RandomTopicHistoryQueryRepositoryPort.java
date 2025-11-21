package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.RandomTopicHistory;

public interface RandomTopicHistoryQueryRepositoryPort {
    RandomTopicHistory getRandomTopicHistoryByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, RandomReqDTO.Next requestDTO);
}