package talkPick.domain.today.application;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    private final CacheManager cacheManager;

    @Override
    public List<TodayTopicResDTO.TodayTopic> getTodayTopics(Long memberId) {
        Cache cache = cacheManager.getCache("todayTopics");
        if (cache != null) {
            Cache.ValueWrapper cachedValue = cache.get(memberId);
            if (cachedValue != null) {
                return (List<TodayTopicResDTO.TodayTopic>) cachedValue.get();
            }
        }

        var todayTopics = todayTopicQueryRepositoryPort.findTodayTopics(memberId);

        if (cache != null) {
            cache.put(memberId, todayTopics);
        }

        publishSavedEvent(memberId, todayTopics);
        return todayTopics;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publishSavedEvent(Long memberId, List<TodayTopicResDTO.TodayTopic> todayTopics) {
        var entities = todayTopics.stream()
                .map(t -> TodayTopic.of(memberId, t.topicId()))
                .toList();
        eventPublisher.publishEvent(TodayTopicSavedEvent.of(this, entities));
    }
}