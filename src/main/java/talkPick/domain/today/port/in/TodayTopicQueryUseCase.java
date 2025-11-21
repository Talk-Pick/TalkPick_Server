package talkPick.domain.today.port.in;

import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;

public interface TodayTopicQueryUseCase {
    List<TodayTopicResDTO.TodayTopic> getTodayTopics(Long memberId);
}