package talkPick.domain.random.adapter.out;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import talkPick.domain.random.dto.TopicDataDTO;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.domain.random.port.out.TopicDataCacheManagerPort;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicDataCacheManagerManager implements TopicDataCacheManagerPort {
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;
    private final List<TopicDataDTO> topicDataCache = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void load() {
        var topicData = randomQueryRepositoryPort.findAllTopicData();
        topicDataCache.clear();
        topicDataCache.addAll(topicData);
        log.info("[TopicDataCacheManager] 캐시 로드 완료: {}개 항목", topicData.size());
    }

    @Override
    public List<TopicDataDTO> getAll() {
        return List.copyOf(topicDataCache);
    }

    @Override
    public void refresh() {
        load();
    }
}