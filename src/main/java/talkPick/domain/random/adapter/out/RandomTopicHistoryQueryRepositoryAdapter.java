package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryQuerydslRepository;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.random.port.out.RandomTopicHistoryQueryRepositoryPort;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RandomTopicHistoryQueryRepositoryAdapter implements RandomTopicHistoryQueryRepositoryPort {
    private final RandomTopicHistoryQuerydslRepository randomTopicHistoryQuerydslRepository;

    @Override
    public List<RandomTopicHistoryDataDTO> getRandomTopicHistoriesByRandomId(Long randomId) {
        return randomTopicHistoryQuerydslRepository.getRandomTopicHistoriesByRandomId(randomId);
    }

    @Override
    public RandomResDTO.Result getResult(Long randomId) {
        return randomTopicHistoryQuerydslRepository.findResultByRandomId(randomId);
    }
}
