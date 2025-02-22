package talkPick.port.out;

import talkPick.adaptor.out.dto.TopicResDTO;

public interface TopicQueryRepositoryPort {
    TopicResDTO.Categories findCategories();
}
