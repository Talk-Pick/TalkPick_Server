package talkPick.domain.search.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.port.in.TopicSearchQueryUseCase;
import talkPick.global.security.annotation.UserId;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class TopicSearchController implements TopicSearchApi {
    private final TopicSearchQueryUseCase topicSearchQueryUseCase;

    @Override
    @GetMapping("/topics")
    public List<TopicSearchResDTO.Topic> getTopics(@RequestParam(required = false) String category) {
        return topicSearchQueryUseCase.getTopics(category);
    }

    @Override
    @GetMapping("/")
    public List<TopicSearchResDTO.Topic> search(@UserId Long memberId, @RequestParam(required = false) String word) {
        return topicSearchQueryUseCase.search(memberId, word);
    }

    @Override
    @GetMapping("/recommend")
    public List<TopicSearchResDTO.Recommendation> recommend() {
        return topicSearchQueryUseCase.recommend();
    }
}