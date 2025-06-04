package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryJpaRepository;
import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.random.port.out.RandomTopicHistoryCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class RandomTopicHistoryCommandRepositoryAdapter implements RandomTopicHistoryCommandRepositoryPort {
    private final RandomTopicHistoryJpaRepository selectedRandomTopicJpaRepository;

    @Override
    public RandomTopicHistory selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        return selectedRandomTopicJpaRepository.save(RandomTopicHistory.ofByCategory(memberId, requestDTO));
    }

    @Override
    public RandomTopicHistory selectTopic(Long memberId, RandomReqDTO.SelectTopic selectTopic) {
        return selectedRandomTopicJpaRepository.save(RandomTopicHistory.ofByTopic(memberId, selectTopic));
    }
}
