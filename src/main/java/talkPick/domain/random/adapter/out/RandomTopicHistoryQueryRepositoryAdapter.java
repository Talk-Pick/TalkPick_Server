package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.SelectedRandomTopicQuerydslRepository;
import talkPick.domain.random.port.out.RandomTopicHistoryQueryRepositoryPort;

@Component
@RequiredArgsConstructor
public class RandomTopicHistoryQueryRepositoryAdapter implements RandomTopicHistoryQueryRepositoryPort {
    private final SelectedRandomTopicQuerydslRepository selectedRandomTopicQuerydslRepository;

    @Override
    public RandomResDTO.Result getResult(Long randomId) {
        return selectedRandomTopicQuerydslRepository.findResultByRandomId(randomId);
    }
}
