package talkPick.domain.today.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.port.in.TodayTopicCommandUseCase;
import talkPick.domain.today.port.out.TodayTopicCommandRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodayTopicCommandService implements TodayTopicCommandUseCase {
    private final TodayTopicCommandRepositoryPort todayTopicCommandRepositoryPort;

    @Override
    public List<TodayTopicResDTO.TodayTopic> getTodayTopicSummaries(Long memberId) {
        List<TodayTopicResDTO.TodayTopic> todayTopics = todayTopicCommandRepositoryPort.findTodayTopicSummaries(memberId);

        todayTopicCommandRepositoryPort.saveAll(todayTopics.stream()
                .map(t -> talkPick.domain.today.domain.TodayTopic.of(memberId, t.topicId()))
                .toList());

        return todayTopics;
    }
}
