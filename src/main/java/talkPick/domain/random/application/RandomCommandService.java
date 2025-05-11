package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.port.in.RandomCommandUseCase;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    @Override
    public void start(Long memberId) {

    }
}
