package talkPick.domain.today.port.in;

import talkPick.domain.today.adapter.in.dto.TodayTopicReqDTO;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;

public interface TodayTopicQueryUseCase {
    List<TodayTopicResDTO.TopicDetail> getTodayTopicDetails(TodayTopicReqDTO.TodayTopics requestDTO);
}
