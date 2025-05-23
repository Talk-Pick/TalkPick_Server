package talkPick.domain.random.port.in;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;

import java.util.List;

public interface RandomQueryUseCase {
    List<RandomResDTO.Categories> getCategories();
//    List<RandomResDTO.RandomTopic> getRandomTopics(Long memberId, Long randomId);
    RandomResDTO.RandomTopicDetail getRandomTopicDetail(Long topicId);
}
