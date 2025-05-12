package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.Random;

public interface RandomCommandRepositoryPort {
    void save(Random random);
    void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
}
