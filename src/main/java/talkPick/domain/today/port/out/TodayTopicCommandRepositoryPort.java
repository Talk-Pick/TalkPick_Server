package talkPick.domain.today.port.out;

import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;

public interface TodayTopicCommandRepositoryPort {
    List<TodayTopicResDTO.TopicSummaries> findTodayTopicSummaries(Long memberId);
}
