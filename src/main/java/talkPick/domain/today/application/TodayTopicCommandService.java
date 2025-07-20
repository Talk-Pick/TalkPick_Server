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
    public List<TodayTopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId) {
        // 새로고침이 가능한 지 확인
        return todayTopicCommandRepositoryPort.findTodayTopicSummaries(userId);
    }
}
