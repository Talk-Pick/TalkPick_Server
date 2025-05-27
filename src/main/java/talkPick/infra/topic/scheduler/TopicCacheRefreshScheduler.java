package talkPick.infra.topic.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.port.out.TopicDataCacheManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicCacheRefreshScheduler {
    private final TopicDataCacheManager topicDataCacheManager;

    @Scheduled(fixedDelay = 5 * 60 * 60 * 1000)
    public void scheduledRefresh() {
        topicDataCacheManager.refresh();
    }
}