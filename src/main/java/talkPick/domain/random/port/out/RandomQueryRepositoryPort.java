package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.Random;

import java.util.List;

public interface RandomQueryRepositoryPort {
    List<RandomResDTO.RandomTopic> findRandomTopics(Long memberId, Long randomId, Integer order, String category);
    Random findRandomByMemberIdAndId(Long memberId, Long randomId);
}