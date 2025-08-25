package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryQuerydslRepository;
import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.random.port.out.RandomTopicHistoryQueryRepositoryPort;
import talkPick.global.exception.handler.RandomExceptionHandler;
import static talkPick.global.exception.ErrorCode.RANDOM_TOPIC_HISTORY_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RandomTopicHistoryQueryRepositoryAdapter implements RandomTopicHistoryQueryRepositoryPort {
    private final RandomTopicHistoryJpaRepository randomTopicHistoryJpaRepository;
    private final RandomTopicHistoryQuerydslRepository randomTopicHistoryQuerydslRepository;

    @Override
    public RandomTopicHistory getRandomTopicHistoryByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, RandomReqDTO.Next requestDTO) {
        return randomTopicHistoryJpaRepository.findByRandomIdAndMemberIdAndOrder(memberId, randomId, requestDTO.order()).orElseThrow(() -> new RandomExceptionHandler(RANDOM_TOPIC_HISTORY_NOT_FOUND));
    }
}
