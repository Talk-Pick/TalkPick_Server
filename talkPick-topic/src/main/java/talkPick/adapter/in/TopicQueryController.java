package talkPick.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.model.PageCustom;
import talkPick.port.in.TopicQueryUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @GetMapping("/top-categories")
    public List<TopicResDTO.Categories> getTopCategories() {
        return topicQueryUseCase.getTopCategories();
    }

    @GetMapping("/total-categories")
    public PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable) {
        return topicQueryUseCase.getCategories(pageable);
    }

    @GetMapping("/today-topics")
    public List<TopicResDTO.Topics> getTodayTopics() {
        return topicQueryUseCase.getTodayTopics();
    }
}
