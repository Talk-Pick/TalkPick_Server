package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomQueryUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RandomQueryController implements RandomQueryApi {
    private final RandomQueryUseCase randomQueryUseCase;

    @Override
    public List<RandomResDTO.Categories> getCategories() {
        return randomQueryUseCase.getCategories();
    }

    @Override
    public RandomResDTO.RandomTopicDetail getRandomTopicDetail(@PathVariable("topicId") Long topicId) {
        return randomQueryUseCase.getRandomTopicDetail(topicId);
    }
}
