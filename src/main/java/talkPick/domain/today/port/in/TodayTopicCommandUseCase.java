package talkPick.domain.today.port.in;

import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;

public interface TodayTopicCommandUseCase {
    List<TodayTopicResDTO.TodayTopic> getTodayTopicSummaries(Long memberId);
}
