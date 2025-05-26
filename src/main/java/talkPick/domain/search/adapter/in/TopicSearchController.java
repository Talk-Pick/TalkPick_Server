package talkPick.domain.search.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.port.in.TopicSearchQueryUseCase;
import talkPick.global.annotation.UserId;
import talkPick.global.common.model.PageCustom;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class TopicSearchController implements TopicSearchApi {
    private final TopicSearchQueryUseCase topicSearchQueryUseCase;

    @Override
    @GetMapping("/topics")
    public List<TopicSearchResDTO.Topic> getTopics(@RequestParam(required = false) String category, Pageable pageable) {
        return topicSearchQueryUseCase.getTopics(category, pageable);
    }

    @Override
    @GetMapping("/")
    public PageCustom<TopicSearchResDTO.Topic> search(@UserId Long memberId, @RequestParam(required = false) String word, Pageable pageable) {
        return topicSearchQueryUseCase.search(memberId, word, pageable);
    }

    @Override
    @GetMapping("/recommend")
    public List<TopicSearchResDTO.Recommendation> recommend() {
        return topicSearchQueryUseCase.recommend();
    }
}