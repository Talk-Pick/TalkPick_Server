package talkPick.domain.search.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.domain.search.port.in.TopicSearchQueryUseCase;
import talkPick.domain.search.port.out.TopicSearchHistoryCommandRepositoryPort;
import talkPick.domain.search.port.out.TopicSearchHistoryQueryRepositoryPort;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.batch.topic.port.TopicCacheManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicSearchQueryService implements TopicSearchQueryUseCase {
    private final TopicCacheManager topicCacheManager;
    private final TopicSearchHistoryCommandRepositoryPort topicSearchHistoryCommandRepositoryPort;
    private final TopicSearchHistoryQueryRepositoryPort topicSearchHistoryQueryRepositoryPort;

    @Override
    public List<TopicSearchResDTO.Topic> getTopics(String category, Pageable pageable) {
        var topicDataList = topicCacheManager.getAll();
        return topicDataList.stream()
                .filter(t -> t.getCategoryTitle().equals(category))
                .map(t -> new TopicSearchResDTO.Topic(
                        t.getId(),
                        t.getTitle(),
                        t.getCategoryTitle(),
                        t.getKeyword(),
                        t.getSelectCount(),
                        t.getAverageTalkTime()
                ))
                .toList();
    }

    @Override
    public List<TopicSearchResDTO.Topic> search(Long memberId, String word) {
        //TODO 테스트할 때 할 일 : 트랜잭션/스레드 다른지 확인
        String threadName = Thread.currentThread().getName();
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object txResource = TransactionSynchronizationManager.getResource("javax.persistence.EntityManager");
        log.info("[Search] thread = {}, tx active = {}, tx resource = {}", threadName, isActive, txResource);

        var cachedTopics = topicCacheManager.getAll();
        var normalizedWord = word.toLowerCase();

        var result = cachedTopics.stream()
                .filter(topic -> containsWord(topic, normalizedWord))
                .map(t -> new TopicSearchResDTO.Topic(
                        t.getId(),
                        t.getTitle(),
                        t.getCategoryTitle(),
                        t.getKeyword(),
                        t.getSelectCount() != null ? t.getSelectCount() : 0,
                        t.getAverageTalkTime()
                ))
                .toList();

        saveSearchHistory(memberId, word, result);

        return result;
    }

    private boolean containsWord(TopicCacheDTO topic, String word) {
        return Stream.of(
                        topic.getTitle(),
                        topic.getDetail(),
                        topic.getKeyword(),
                        topic.getCategoryGroup(),
                        topic.getCategoryTitle(),
                        topic.getCategoryDescription()
                )
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .anyMatch(field -> field.contains(word));
    }

    private void saveSearchHistory(Long memberId, String word, List<TopicSearchResDTO.Topic> result) {
        topicSearchHistoryCommandRepositoryPort.save(memberId, word, result != null && !result.isEmpty());
    }

    @Override
    public List<TopicSearchResDTO.Recommendation> recommend() {
        return topicSearchHistoryQueryRepositoryPort.recommend();
    }
}