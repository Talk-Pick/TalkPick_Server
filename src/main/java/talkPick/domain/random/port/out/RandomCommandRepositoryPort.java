package talkPick.domain.random.port.out;

import talkPick.domain.random.domain.Random;

public interface RandomCommandRepositoryPort {
    void save(Random random);
}
