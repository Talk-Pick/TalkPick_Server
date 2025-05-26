package talkPick.domain.random.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomQueryUseCase;
import talkPick.global.annotation.UserId;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/random")
public class RandomQueryController implements RandomQueryApi {
    private final RandomQueryUseCase randomQueryUseCase;

    @Override
    @GetMapping("/categories")
    public List<RandomResDTO.Categories> getCategories() {
        return randomQueryUseCase.getCategories();
    }

    @Override
    @GetMapping("/{topicId}")
    public RandomResDTO.RandomTopicDetail getRandomTopicDetail(@PathVariable("topicId") Long topicId) {
        return randomQueryUseCase.getRandomTopicDetail(topicId);
    }
}
