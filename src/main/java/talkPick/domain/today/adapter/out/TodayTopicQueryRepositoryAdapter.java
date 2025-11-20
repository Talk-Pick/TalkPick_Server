package talkPick.domain.today.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.adapter.out.repository.TodayTopicQuerydslRepository;
import talkPick.domain.today.port.out.TodayTopicQueryRepositoryPort;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodayTopicQueryRepositoryAdapter implements TodayTopicQueryRepositoryPort {
    private final TodayTopicQuerydslRepository todayTopicQuerydslRepository;

    @Override
    public List<TodayTopicResDTO.TodayTopic> findTodayTopics(Long memberId) {
        return todayTopicQuerydslRepository.findTodayTopics(memberId);
    }

    @Override
    public List<TodayTopicResDTO.TodayTopic> findTodayTopicsByDateOnly(Long memberId) {
        return todayTopicQuerydslRepository.findTodayTopicsByDate(memberId);
    }
}