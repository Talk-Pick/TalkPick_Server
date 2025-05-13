package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;

public interface RandomTopicCommandRepositoryPort {
    void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
}
