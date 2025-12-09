package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.domain.random.port.out.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;
    private final RandomCommandRepositoryPort randomCommandRepositoryPort;
    private final RandomTopicHistoryCommandRepositoryPort randomTopicHistoryCommandRepositoryPort;
    private final RandomTopicHistoryQueryRepositoryPort randomTopicHistoryQueryRepositoryPort;

    @Override
    public RandomResDTO.RandomStart start(Long memberId) {
        return RandomResDTO.RandomStart.from(
                randomCommandRepositoryPort.save(Random.from(memberId)).getId()
        );
    }

    @Override
    public void next(Long memberId, Long randomId, RandomReqDTO.Next requestDTO) {
        randomTopicHistoryQueryRepositoryPort.getRandomTopicHistoryByMemberIdAndRandomIdAndOrder(memberId, randomId, requestDTO).next();
    }

    @Override
    public void quit(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).quit();
    }

    @Override
    public void end(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).end();
    }

    @Override
    public void record(Long memberId, Long randomId, RandomReqDTO.Record requestDTO) {
        randomTopicHistoryCommandRepositoryPort.record(memberId, randomId, requestDTO);
    }

    @Override
    public void rate(Long memberId, Long randomId, RandomReqDTO.Rate requestDTO) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).rate(requestDTO);
    }

    @Override
    public void comment(Long memberId, Long randomId, RandomReqDTO.Comment requestDTO) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).comment(requestDTO);
    }
}
