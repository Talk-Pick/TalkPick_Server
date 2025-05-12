package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

import java.util.List;

public interface RandomQueryRepositoryPort {
    List<RandomResDTO.Categories> findCategories();
    RandomResDTO.RandomTopicDetail findRandomTopicDetail(Long topicId);
    List<String> findTopicImages(Long topicId);
}