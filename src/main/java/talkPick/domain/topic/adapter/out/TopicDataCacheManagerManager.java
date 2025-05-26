package talkPick.domain.topic.adapter.out;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.dto.TopicDataDTO;
import talkPick.domain.topic.port.out.TopicDataCacheManagerPort;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicDataCacheManagerManager implements TopicDataCacheManagerPort {
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;
    private final List<TopicDataDTO> topicDataCache = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void load() {
        var topicData = topicQueryRepositoryPort.findAllTopicData();
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