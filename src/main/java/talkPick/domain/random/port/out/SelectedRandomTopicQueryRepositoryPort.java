package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;

public interface SelectedRandomTopicQueryRepositoryPort {
    RandomResDTO.Result getResult(Long randomId);
}