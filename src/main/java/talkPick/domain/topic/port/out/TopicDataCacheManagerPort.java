package talkPick.domain.topic.port.out;

import talkPick.domain.random.dto.TopicDataDTO;

import java.util.List;

public interface TopicDataCacheManagerPort {
    List<TopicDataDTO> getAll();
    void refresh();
}