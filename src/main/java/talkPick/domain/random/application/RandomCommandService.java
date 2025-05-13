package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.domain.random.port.out.RandomCommandRepositoryPort;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.domain.random.port.out.SelectedRandomTopicCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    private final RandomCommandRepositoryPort randomCommandRepositoryPort;
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;
    private final SelectedRandomTopicCommandRepositoryPort randomTopicCommandRepositoryPort;

    @Override
    public void start(Long memberId) {
        randomCommandRepositoryPort.save(Random.from(memberId));
    }

    @Override
    public void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        randomTopicCommandRepositoryPort.selectCategory(memberId, requestDTO);
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, requestDTO.randomId()).start();
    }

    @Override
    public void selectTopic(Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        randomTopicCommandRepositoryPort.selectTopic(memberId, requestDTO);
    }

    @Override
    public void quit(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).quit();
    }
}
