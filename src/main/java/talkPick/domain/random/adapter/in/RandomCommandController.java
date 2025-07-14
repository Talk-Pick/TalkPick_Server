package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.global.security.annotation.UserId;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RandomCommandController implements RandomCommandApi {
    private final RandomCommandUseCase randomCommandUseCase;

    @Override
    public void start(@UserId final Long memberId) {
        randomCommandUseCase.start(memberId);
    }

    @Override
    public List<RandomResDTO.RandomTopic> selectByCategories(@UserId Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        return randomCommandUseCase.selectByCategories(memberId, requestDTO);
    }

    @Override
    public List<RandomResDTO.RandomTopic> selectByTopics(@UserId Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        return randomCommandUseCase.selectByTopics(memberId, requestDTO);
    }

    @Override
    public void quit(@UserId Long memberId, @PathVariable("randomId") Long randomId) {
        randomCommandUseCase.quit(memberId, randomId);
    }

    @Override
    public RandomResDTO.Result end(Long memberId, @PathVariable("randomId") Long randomId) {
        return randomCommandUseCase.end(memberId, randomId);
    }

    @Override
    public void saveResult(Long memberId, @PathVariable("randomId") Long randomId, RandomReqDTO.Result requestDTO) {
        randomCommandUseCase.saveResult(memberId, randomId, requestDTO);
    }
}