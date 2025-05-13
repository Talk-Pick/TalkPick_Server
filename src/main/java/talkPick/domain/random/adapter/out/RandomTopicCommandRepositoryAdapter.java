package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.repository.RandomTopicJpaRepository;
import talkPick.domain.random.domain.RandomTopic;
import talkPick.domain.random.port.out.RandomTopicCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class RandomTopicCommandRepositoryAdapter implements RandomTopicCommandRepositoryPort {
    private final RandomTopicJpaRepository randomTopicJpaRepository;

    @Override
    public void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        randomTopicJpaRepository.save(RandomTopic.of(memberId, requestDTO));
    }
}
