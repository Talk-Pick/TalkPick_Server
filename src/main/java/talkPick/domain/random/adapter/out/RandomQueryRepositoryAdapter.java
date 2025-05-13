package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomQuerydslRepository;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.exception.RandomNotFoundException;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.global.error.ErrorCode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RandomQueryRepositoryAdapter implements RandomQueryRepositoryPort {
    private final RandomJpaRepository randomJpaRepository;
    private final RandomQuerydslRepository randomQuerydslRepository;

    @Override
    public List<RandomResDTO.Categories> findCategories() {
        return randomQuerydslRepository.findCategories();
    }

    @Override
    public RandomResDTO.RandomTopicDetail findRandomTopicDetail(Long topicId) {
        return randomQuerydslRepository.findRandomTopicDetail(topicId);
    }

    @Override
    public List<String> findRandomTopicImages(Long topicId) {
        return randomQuerydslRepository.findRandomTopicImages(topicId);
    }

    @Override
    public Random findRandomByMemberIdAndId(Long memberId, Long randomId) {
        return randomJpaRepository.findRandomByMemberIdAndId(memberId, randomId).orElseThrow(() -> new RandomNotFoundException(ErrorCode.RANDOM_NOT_FOUND));
    }
}
