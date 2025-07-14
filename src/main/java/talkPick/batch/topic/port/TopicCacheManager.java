package talkPick.batch.topic.port;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.topic.dto.TopicCacheDTO;

import java.util.List;

public interface TopicCacheManager {
    List<TopicCacheDTO> getAll();
    void refresh();
    List<RandomResDTO.RandomTopic> getRandomTopics(final Integer order);
}