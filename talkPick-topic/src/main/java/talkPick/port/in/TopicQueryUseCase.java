package talkPick.port.in;

import talkPick.adaptor.out.dto.TopicResDTO;
import java.util.List;

public interface TopicQueryUseCase {
    List<TopicResDTO.TopCategories> getTopCategories();
}
