package talkPick.domain.search.port.out;

public interface TopicSearchHistoryCommandRepositoryPort {
    void save(Long memberId, String keyword, Boolean isResultShown);
}