package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;

import java.util.List;

public interface RandomTopicHistoryQueryRepositoryPort {
    RandomTopicHistory getRandomTopicHistoryByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, RandomReqDTO.Next requestDTO);
    List<RandomTopicHistoryDataDTO > getRandomTopicHistoriesByRandomId(Long randomId);
    RandomResDTO.Result getResult(Long randomId);
}