package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;

public interface RandomTopicHistoryCommandRepositoryPort {
    void record(Long memberId, Long randomId, RandomReqDTO.Record requestDTO);
}
