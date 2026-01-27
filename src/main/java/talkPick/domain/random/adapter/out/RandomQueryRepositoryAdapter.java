package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomQuerydslRepository;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.core.common.exception.handler.RandomExceptionHandler;
import java.util.List;
import static talkPick.core.common.exception.ErrorCode.RANDOM_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RandomQueryRepositoryAdapter implements RandomQueryRepositoryPort {
    private final RandomJpaRepository randomJpaRepository;
    private final RandomQuerydslRepository randomQuerydslRepository;

    @Override
    public List<RandomResDTO.RandomTopic> findRandomTopics(Long memberId, Long randomId, Integer order, String category) {
        var topics = randomQuerydslRepository.findRandomTopics(memberId, randomId, category);
        return List.of(new RandomResDTO.RandomTopic(order, topics));
    }

    @Override
    public Random findRandomByMemberIdAndId(Long memberId, Long randomId) {
        return randomJpaRepository.findRandomByMemberIdAndId(memberId, randomId).orElseThrow(() -> new RandomExceptionHandler(RANDOM_NOT_FOUND));
    }
}