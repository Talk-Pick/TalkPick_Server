package talkPick.adaptor.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.port.in.TopicQueryUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
public class TopicQueryController implements TopicQueryApi {
    private final TopicQueryUseCase topicQueryUseCase;

    @GetMapping("/categories")
    public TopicResDTO.Categories getCategories() {
        return topicQueryUseCase.getCategories();
    }
}
