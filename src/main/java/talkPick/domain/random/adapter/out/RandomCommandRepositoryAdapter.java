package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomQuerydslRepository;
import talkPick.domain.random.adapter.out.repository.RandomTopicJpaRepository;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.domain.RandomTopic;
import talkPick.domain.random.port.out.RandomCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class RandomCommandRepositoryAdapter implements RandomCommandRepositoryPort {
    private final RandomJpaRepository randomJpaRepository;
    private final RandomQuerydslRepository randomQuerydslRepository;
    private final RandomTopicJpaRepository randomTopicJpaRepository;

    @Override
    public void save(Random random) {
        randomJpaRepository.save(random);
    }

    @Override
    public void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        randomTopicJpaRepository.save(RandomTopic.of(memberId, requestDTO));
    }
}
