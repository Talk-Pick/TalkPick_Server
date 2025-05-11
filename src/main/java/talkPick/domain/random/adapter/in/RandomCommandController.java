package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.port.in.RandomCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/random")
public class RandomCommandController implements RandomCommandApi {
    private final RandomCommandUseCase randomCommandUseCase;
}