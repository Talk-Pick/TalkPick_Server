package talkPick.domain.today.port.out;

import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;

public interface TodayTopicQueryRepositoryPort {
    List<TodayTopicResDTO.TodayTopic> findTodayTopics(Long memberId);
}
