package talkPick.domain.today.port.out;

import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.domain.TodayTopic;

import java.util.List;

public interface TodayTopicCommandRepositoryPort {
    List<TodayTopicResDTO.TopicSummaries> findTodayTopicSummaries(Long memberId);
    void saveAll(List<TodayTopic> todayTopics);
}
