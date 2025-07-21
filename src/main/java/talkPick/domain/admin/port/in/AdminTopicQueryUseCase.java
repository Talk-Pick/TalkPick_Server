package talkPick.domain.admin.port.in;

import talkPick.domain.today.adapter.in.dto.TodayTopicReqDTO;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

import java.util.List;

public interface AdminTopicQueryUseCase {
    List<TodayTopicResDTO.TopicSummaries> getTopic(Long memberId);
    List<TopicResDTO.TopicDetail> getDetailTopic(TodayTopicReqDTO.TodayTopics requestDTO);
}
