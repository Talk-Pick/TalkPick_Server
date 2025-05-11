package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.domain.random.port.out.RandomCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    private final RandomCommandRepositoryPort randomCommandRepositoryPort;

    @Override
    public void start(Long memberId) {
        randomCommandRepositoryPort.save(Random.of(memberId));
    }
}
