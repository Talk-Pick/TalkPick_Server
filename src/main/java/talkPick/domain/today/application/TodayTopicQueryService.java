package talkPick.domain.today.application;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.domain.TodayTopic;
import talkPick.domain.today.domain.event.TodayTopicSavedEvent;
import talkPick.domain.today.port.in.TodayTopicQueryUseCase;
import talkPick.domain.today.port.out.TodayTopicQueryRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodayTopicQueryService implements TodayTopicQueryUseCase {
    private final TodayTopicQueryRepositoryPort todayTopicQueryRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Cacheable(value = "todayTopics", key = "#memberId")
    public List<TodayTopicResDTO.TodayTopic> getTodayTopics(Long memberId) {
        var todayTopics = todayTopicQueryRepositoryPort.findTodayTopics(memberId);
        publishSavedEvent(memberId, todayTopics);
        return todayTopics;
    }

    private void publishSavedEvent(Long memberId, List<TodayTopicResDTO.TodayTopic> todayTopics) {
        var entities = todayTopics.stream()
                .map(t -> TodayTopic.of(memberId, t.topicId()))
                .toList();
        eventPublisher.publishEvent(TodayTopicSavedEvent.of(this, entities));
    }
}