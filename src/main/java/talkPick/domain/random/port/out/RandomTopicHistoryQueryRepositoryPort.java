package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;

public interface RandomTopicHistoryQueryRepositoryPort {
    RandomResDTO.Result getResult(Long randomId);
}