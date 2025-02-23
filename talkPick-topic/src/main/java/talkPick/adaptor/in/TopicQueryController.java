package talkPick.adaptor.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.port.in.TopicQueryUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @GetMapping("/categories")
    public List<TopicResDTO.Categories> getTopCategories() {
        return topicQueryUseCase.getTopCategories();
    }
}
