package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.dto.TopicDataDTO;

import java.util.List;

public interface RandomQueryRepositoryPort {
    List<RandomResDTO.Categories> findCategories();
    RandomResDTO.RandomTopicDetail findRandomTopicDetail(Long topicId);
    List<String> findRandomTopicImages(Long topicId);
    Random findRandomByMemberIdAndId(Long memberId, Long randomId);
    List<TopicDataDTO> findAllTopicData();
}