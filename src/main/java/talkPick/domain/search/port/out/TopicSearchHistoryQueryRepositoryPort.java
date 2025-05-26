package talkPick.domain.search.port.out;

import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;

import java.util.List;

public interface TopicSearchHistoryQueryRepositoryPort {
    List<TopicSearchResDTO.Recommendation> recommend();
}