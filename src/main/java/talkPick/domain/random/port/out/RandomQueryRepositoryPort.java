package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.Random;
import java.util.List;

public interface RandomQueryRepositoryPort {
    List<RandomResDTO.Categories> findCategories();
    Random findRandomByMemberIdAndId(Long memberId, Long randomId);
    List<RandomResDTO.RandomTopic> findRandomTopicsByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, Integer order);
    List<String> findRandomTopicImages(Long topicId);
}