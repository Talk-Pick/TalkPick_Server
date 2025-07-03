package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.global.security.annotation.UserId;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/random")
public class RandomCommandController implements RandomCommandApi {
    private final RandomCommandUseCase randomCommandUseCase;

    @Override
    @PostMapping("/start")
    public void start(@UserId final Long memberId) {
        randomCommandUseCase.start(memberId);
    }

    @Override
    @PostMapping("/select-category")
    public List<RandomResDTO.RandomTopic> selectCategory(@UserId Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        return randomCommandUseCase.selectCategory(memberId, requestDTO);
    }

    @Override
    @PostMapping("/select-topic")
    public List<RandomResDTO.RandomTopic> selectTopic(@UserId Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        return randomCommandUseCase.selectTopic(memberId, requestDTO);
    }

    @Override
    @PostMapping("/quit/{randomId}")
    public void quit(@UserId Long memberId, @PathVariable("randomId") Long randomId) {
        randomCommandUseCase.quit(memberId, randomId);
    }

    @Override
    @PostMapping("/end/{randomId}")
    public RandomResDTO.Result end(Long memberId, @PathVariable("randomId") Long randomId) {
        return randomCommandUseCase.end(memberId, randomId);
    }

    @Override
    @PostMapping("/save-result/{randomId}")
    public void saveResult(Long memberId, @PathVariable("randomId") Long randomId, RandomReqDTO.Result requestDTO) {
        randomCommandUseCase.saveResult(memberId, randomId, requestDTO);
    }
}