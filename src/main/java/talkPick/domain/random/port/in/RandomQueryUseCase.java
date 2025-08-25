package talkPick.domain.random.port.in;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.topic.domain.type.CategoryGroup;
import java.util.List;

public interface RandomQueryUseCase {
    List<RandomResDTO.RandomTopic> getRandomTopics(Long memberId, Long randomId, Integer order, CategoryGroup categoryGroup, String category);
}
