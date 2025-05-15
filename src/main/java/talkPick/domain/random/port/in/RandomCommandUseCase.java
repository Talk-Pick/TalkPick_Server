package talkPick.domain.random.port.in;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;

public interface RandomCommandUseCase {
    void start(Long memberId);
    void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
    void selectTopic(Long memberId, RandomReqDTO.SelectTopic requestDTO);
    void quit(Long memberId, Long randomId);
    RandomResDTO.Result end(Long memberId, Long randomId);
    void saveResult(Long memberId, Long randomId, RandomReqDTO.Result requestDTO);
}
