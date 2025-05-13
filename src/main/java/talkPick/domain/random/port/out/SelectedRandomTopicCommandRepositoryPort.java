package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;

public interface SelectedRandomTopicCommandRepositoryPort {
    void selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO);
    void selectTopic(Long memberId, RandomReqDTO.SelectTopic selectTopic);
}
