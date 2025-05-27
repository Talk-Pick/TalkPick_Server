package talkPick.domain.topic.port.out;

import talkPick.domain.topic.dto.TopicDataDTO;

import java.util.List;

public interface TopicDataCacheManager {
    List<TopicDataDTO> getAll();
    void refresh();
}