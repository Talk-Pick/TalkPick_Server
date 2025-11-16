package talkPick.batch.today.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodayTopicCacheRefreshScheduler {
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 0 * * *")
    public void clearTodayTopicsCacheAtMidnight() {
        try {
            var cache = cacheManager.getCache("todayTopics");
            if (cache != null) {
                cache.clear();
                log.info("오늘의 주제 캐시가 자정에 삭제되었습니다");
            }
        } catch (Exception e) {
            log.error("오늘의 주제 캐시 삭제 중 오류 발생", e);
        }
    }
}