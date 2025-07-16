package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;

import java.util.List;

public interface RandomTopicHistoryQueryRepositoryPort {
    List<RandomTopicHistoryDataDTO > getRandomTopicHistoriesByRandomId(Long randomId);
    RandomResDTO.Result getResult(Long randomId);
}