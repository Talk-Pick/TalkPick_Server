package talkPick.infra.topic.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.port.out.TopicCacheManager;

@Component
@RequiredArgsConstructor
public class TopicCacheRefreshScheduler {
    private final TopicCacheManager topicCacheManager;

    @Scheduled(fixedDelay = 5 * 60 * 60 * 1000)
    public void scheduledRefresh() {
        topicCacheManager.refresh();
    }
}