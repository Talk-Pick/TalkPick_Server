package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.port.in.RandomCommandUseCase;

@RestController
@RequiredArgsConstructor
public class RandomCommandController implements RandomCommandApi {
    private final RandomCommandUseCase randomCommandUseCase;

    @Override
    public void start(Long memberId) {
        randomCommandUseCase.start(memberId);
    }

    @Override
    public void quit(Long memberId, Long randomId) {
        randomCommandUseCase.quit(memberId, randomId);
    }

    @Override
    public void end(Long memberId, Long randomId) {
        return randomCommandUseCase.end(memberId, randomId);
    }
}