package talkPick.domain.today.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.port.in.TodayTopicQueryUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodayTopicQueryController implements TodayTopicQueryApi {
    private final TodayTopicQueryUseCase todayTopicQueryUseCase;

    @Override
    public List<TodayTopicResDTO.TodayTopic> getTodayTopics(Long memberId) {
        return todayTopicQueryUseCase.getTodayTopics(memberId);
    }
}