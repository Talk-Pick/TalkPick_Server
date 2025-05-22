package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.repository.SelectedRandomTopicJpaRepository;
import talkPick.domain.random.domain.SelectedRandomTopic;
import talkPick.domain.random.port.out.SelectedRandomTopicCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class SelectedRandomTopicCommandRepositoryAdapter implements SelectedRandomTopicCommandRepositoryPort {
    private final SelectedRandomTopicJpaRepository selectedRandomTopicJpaRepository;

    @Override
    public void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        selectedRandomTopicJpaRepository.save(SelectedRandomTopic.ofByCategory(memberId, requestDTO));
    }

    @Override
    public void selectTopic(Long memberId, RandomReqDTO.SelectTopic selectTopic) {
        selectedRandomTopicJpaRepository.save(SelectedRandomTopic.ofByTopic(memberId, selectTopic));
    }
}
