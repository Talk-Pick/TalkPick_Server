package talkPick.port.in;

import org.springframework.data.domain.Pageable;
import talkPick.adapter.out.dto.TopicResDTO;
import talkPick.model.PageCustom;
import java.util.List;

public interface TopicQueryUseCase {
    List<TopicResDTO.Categories> getTopCategories();
    PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable);
    List<TopicResDTO.Topics> getTodayTopics(final Long memberId);
}