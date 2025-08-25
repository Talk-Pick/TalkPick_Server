package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomQueryUseCase;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.domain.topic.domain.type.CategoryGroup;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomQueryService implements RandomQueryUseCase {
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;

    @Override
    public List<RandomResDTO.RandomTopic> getRandomTopics(Long memberId, Long randomId, Integer order, CategoryGroup categoryGroup, String category) {
        return randomQueryRepositoryPort.findRandomTopics(memberId, randomId, order, categoryGroup, category);
    }
}