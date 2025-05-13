package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.domain.random.port.out.RandomCommandRepositoryPort;
import talkPick.domain.random.port.out.RandomTopicCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    private final RandomCommandRepositoryPort randomCommandRepositoryPort;
    private final RandomTopicCommandRepositoryPort randomTopicCommandRepositoryPort;

    @Override
    public void start(Long memberId) {
        randomCommandRepositoryPort.save(Random.from(memberId));
    }

    @Override
    public void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        randomTopicCommandRepositoryPort.selectCategory(memberId, requestDTO);
    }
}
