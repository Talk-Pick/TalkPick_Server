package talkPick.port.out;

import org.springframework.data.domain.Pageable;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.model.PageCustom;

import java.util.List;

public interface TopicQueryRepositoryPort {
    List<TopicResDTO.Categories> findTopCategories();
    PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable);
}
