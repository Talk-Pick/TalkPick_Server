package talkPick.port.out;

import talkPick.adaptor.out.dto.TopicResDTO;
import java.util.List;

public interface TopicQueryRepositoryPort {
    List<TopicResDTO.TopCategories> findTopCategories();
}
