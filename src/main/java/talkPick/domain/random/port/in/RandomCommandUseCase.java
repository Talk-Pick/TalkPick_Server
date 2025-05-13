package talkPick.domain.random.port.in;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;

public interface RandomCommandUseCase {
    void start(Long memberId);
    void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
    void selectTopic(Long memberId, RandomReqDTO.SelectTopic requestDTO);
}
