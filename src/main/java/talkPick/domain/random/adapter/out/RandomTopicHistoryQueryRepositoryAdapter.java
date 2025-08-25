package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryQuerydslRepository;
import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.random.port.out.RandomTopicHistoryQueryRepositoryPort;
import talkPick.global.exception.handler.RandomExceptionHandler;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<RandomTopicHistoryDataDTO> getRandomTopicHistoriesByRandomId(Long randomId) {
        return randomTopicHistoryQuerydslRepository.getRandomTopicHistoriesByRandomId(randomId);
    }

    @Override
    public RandomResDTO.Result getResult(Long randomId) {
        return Optional.ofNullable(randomTopicHistoryQuerydslRepository.findResultByRandomId(randomId))
                .orElseThrow(() -> new RandomExceptionHandler(RANDOM_TOPIC_HISTORY_NOT_FOUND));
    }
}
