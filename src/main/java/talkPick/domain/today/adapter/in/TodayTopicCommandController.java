package talkPick.domain.today.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.port.in.TodayTopicCommandUseCase;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodayTopicCommandController implements TodayTopicCommandApi {
    private final TodayTopicCommandUseCase todayTopicCommandUseCase;

    @Override
    public List<TodayTopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId) {
        return todayTopicCommandUseCase.getTodayTopicSummaries(userId);
    }
}