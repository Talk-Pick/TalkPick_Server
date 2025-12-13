package talkPick.domain.random.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryJpaRepository;
import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.random.port.out.RandomTopicHistoryCommandRepositoryPort;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RandomTopicHistoryCommandRepositoryAdapter implements RandomTopicHistoryCommandRepositoryPort {
    private final RandomTopicHistoryJpaRepository randomTopicHistoryJpaRepository;

    @Override
    public void record(Long memberId, Long randomId, RandomReqDTO.Record requestDTO) {
        randomTopicHistoryJpaRepository.save(RandomTopicHistory.of(memberId, randomId, requestDTO));
    }

    @Override
    public void totalRecord(Long memberId, Long randomId, RandomReqDTO.TotalRecords requestDTO) {
        List<RandomTopicHistory> entities =
                requestDTO.totalRecords().stream()
                        .map(r -> RandomTopicHistory.ofRecord(memberId, randomId, r))
                        .toList();

        randomTopicHistoryJpaRepository.saveAll(entities);
    }
}
