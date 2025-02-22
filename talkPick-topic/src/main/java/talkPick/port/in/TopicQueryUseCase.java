package talkPick.port.in;

import talkPick.adaptor.out.dto.TopicResDTO;

public interface TopicQueryUseCase {
    TopicResDTO.Categories getCategories();
}
