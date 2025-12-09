package talkPick.domain.random.port.in;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;

public interface RandomCommandUseCase {
    RandomResDTO.RandomStart start(Long memberId);
    void next(Long memberId, Long randomId, RandomReqDTO.Next requestDTO);
    void quit(Long memberId, Long randomId);
    void end(Long memberId, Long randomId);
    void record(Long memberId, Long randomId, RandomReqDTO.Record requestDTO);
    void rate(Long memberId, Long randomId, RandomReqDTO.Rate requestDTO);
    void comment(Long memberId, Long randomId, RandomReqDTO.Comment requestDTO);
}
