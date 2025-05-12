package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomQuerydslRepository;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
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
    public List<String> findTopicImages(Long topicId) {
        return randomQuerydslRepository.findRandomTopicImages(topicId);
    }
}
