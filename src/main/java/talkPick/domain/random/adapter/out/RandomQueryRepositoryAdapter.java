package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomQuerydslRepository;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.global.exception.handler.RandomExceptionHandler;
import java.util.List;
import static talkPick.global.exception.ErrorCode.RANDOM_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RandomQueryRepositoryAdapter implements RandomQueryRepositoryPort {
    private final RandomJpaRepository randomJpaRepository;
    private final RandomQuerydslRepository randomQuerydslRepository;

    @Override
    public List<RandomResDTO.Categories> findCategories() {
        return randomQuerydslRepository.findCategories();
    }

    @Override
    public List<RandomResDTO.RandomTopic> findRandomTopicsByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, Integer order) {
        var topics = randomQuerydslRepository.findRandomTopicsExcludingHistory(memberId, randomId, 4);

        topics.forEach(t -> t.addOrder(order));
        return topics;
    }

    @Override
    public List<String> findRandomTopicImages(Long topicId) {
        return randomQuerydslRepository.findRandomTopicImages(topicId);
    }

    @Override
    public Random findRandomByMemberIdAndId(Long memberId, Long randomId) {
        return randomJpaRepository.findRandomByMemberIdAndId(memberId, randomId).orElseThrow(() -> new RandomExceptionHandler(RANDOM_NOT_FOUND));
    }
}