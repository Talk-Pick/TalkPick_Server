package talkPick.domain.search.port.out;

import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;

import java.util.List;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
public interface TopicSearchHistoryQueryRepositoryPort {
    List<TopicSearchResDTO.Recommendation> recommend();
}