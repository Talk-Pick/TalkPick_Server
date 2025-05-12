package talkPick.domain.random.port.out;

import talkPick.domain.random.adapter.out.dto.RandomResDTO;

import java.util.List;

public interface RandomQueryRepositoryPort {
    List<RandomResDTO.Categories> findCategories();
}
