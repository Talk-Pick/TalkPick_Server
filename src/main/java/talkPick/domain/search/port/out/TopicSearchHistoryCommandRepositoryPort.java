package talkPick.domain.search.port.out;

/**
 * 해당 코드 사용 안 함.
 * **/
@Deprecated
public interface TopicSearchHistoryCommandRepositoryPort {
    void save(Long memberId, String word, Boolean isResultShown);
}