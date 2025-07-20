package talkPick.domain.today.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.adapter.out.repository.TodayTopicQuerydslRepository;
import talkPick.domain.today.port.out.TodayTopicCommandRepositoryPort;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodayTopicCommandRepositoryAdapter implements TodayTopicCommandRepositoryPort {
    private final TodayTopicQuerydslRepository todayTopicQuerydslRepository;

    @Override
    public List<TodayTopicResDTO.TopicSummaries> findTodayTopicSummaries(Long memberId) {
        return todayTopicQuerydslRepository.findTodayTopicSummaries(memberId);
    }
}
