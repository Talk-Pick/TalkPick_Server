package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomCommandUseCase;

@RestController
@RequiredArgsConstructor
public class RandomCommandController implements RandomCommandApi {
    private final RandomCommandUseCase randomCommandUseCase;

    @Override
    public RandomResDTO.RandomStart start(Long memberId) {
        return randomCommandUseCase.start(memberId);
    }

    @Override
    public void next(Long memberId, Long randomId, RandomReqDTO.Next requestDTO) {
        randomCommandUseCase.next(memberId, randomId, requestDTO);
    }

    @Override
    public void quit(Long memberId, Long randomId) {
        randomCommandUseCase.quit(memberId, randomId);
    }

    @Override
    public void end(Long memberId, Long randomId) {
        randomCommandUseCase.end(memberId, randomId);
    }

    @Override
    public void totalRecord(Long memberId, Long randomId, RandomReqDTO.TotalRecords requestDTO) {
        randomCommandUseCase.totalRecord(memberId, randomId, requestDTO);
    }

    @Override
    public void record(Long memberId, Long randomId, RandomReqDTO.Record requestDTO) {
        randomCommandUseCase.record(memberId, randomId, requestDTO);
    }

    @Override
    public void rate(Long memberId, Long randomId, RandomReqDTO.Rate requestDTO) {
        randomCommandUseCase.rate(memberId, randomId, requestDTO);
    }

    @Override
    public void comment(Long memberId, Long randomId, RandomReqDTO.Comment requestDTO) {
        randomCommandUseCase.comment(memberId, randomId, requestDTO);
    }
}