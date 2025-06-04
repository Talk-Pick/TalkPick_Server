package talkPick.domain.topic.port.out;

import talkPick.domain.topic.dto.TopicCacheDTO;

import java.util.List;

public interface TopicCacheManager {
    List<TopicCacheDTO> getAll();
    void refresh();
}