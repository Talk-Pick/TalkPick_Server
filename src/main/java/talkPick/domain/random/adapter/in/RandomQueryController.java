package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.port.in.RandomQueryUseCase;

@RestController
@RequiredArgsConstructor
public class RandomQueryController implements RandomQueryApi {
    private final RandomQueryUseCase randomQueryUseCase;


}
