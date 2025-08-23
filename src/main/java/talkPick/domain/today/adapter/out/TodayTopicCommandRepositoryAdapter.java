package talkPick.domain.today.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.adapter.out.repository.TodayTopicJpaRepository;
import talkPick.domain.today.adapter.out.repository.TodayTopicQuerydslRepository;
import talkPick.domain.today.domain.TodayTopic;
import talkPick.domain.today.port.out.TodayTopicCommandRepositoryPort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component

@RequiredArgsConstructor
public class TodayTopicCommandRepositoryAdapter implements TodayTopicCommandRepositoryPort {
    private final TodayTopicJpaRepository topicJpaRepository;
    private final TodayTopicQuerydslRepository todayTopicQuerydslRepository;

    @Override
    public List<TodayTopicResDTO.TopicSummaries> findTodayTopicSummaries(Long memberId) {
        return Optional.ofNullable(todayTopicQuerydslRepository.findTodayTopicSummaries(memberId))
                .orElse(Collections.emptyList());
    }

    @Override
    public void saveAll(List<TodayTopic> todayTopics) {
        if (!Optional.ofNullable(todayTopics).orElse(Collections.emptyList()).isEmpty()) {
            topicJpaRepository.saveAll(todayTopics);
        }
    }
}
