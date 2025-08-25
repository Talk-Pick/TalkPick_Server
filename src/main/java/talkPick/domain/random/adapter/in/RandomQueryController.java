package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomQueryUseCase;
import talkPick.domain.topic.domain.type.CategoryGroup;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RandomQueryController implements RandomQueryApi {
    private final RandomQueryUseCase randomQueryUseCase;

    @Override
    public List<RandomResDTO.RandomTopic> getRandomTopics(Long memberId, Long randomId, Integer order, CategoryGroup categoryGroup, String category) {
        return randomQueryUseCase.getRandomTopics(memberId, randomId, order, categoryGroup, category);
    }
}
