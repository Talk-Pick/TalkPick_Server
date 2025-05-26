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
import talkPick.domain.search.port.out.TopicSearchQueryRepositoryPort;
import talkPick.domain.topic.port.out.TopicDataCacheManagerPort;
import talkPick.global.common.model.PageCustom;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicSearchQueryService implements TopicSearchQueryUseCase {
    private final TopicSearchQueryRepositoryPort searchQueryRepositoryPort;
    private final TopicDataCacheManagerPort topicDataCacheManagerPort;
    private final TopicSearchHistoryCommandRepositoryPort topicSearchHistoryCommandRepositoryPort;

    @Override
    public List<TopicSearchResDTO.Topic> getTopics(String category, Pageable pageable) {
        var topicDataList = topicDataCacheManagerPort.getAll();
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
    public PageCustom<TopicSearchResDTO.Topic> search(Long memberId, String word, Pageable pageable) {
        //TODO 테스트할 때 할 일 : 트랜잭션/스레드 다른지 확인
        String threadName = Thread.currentThread().getName();
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object txResource = TransactionSynchronizationManager.getResource("javax.persistence.EntityManager");
        log.info("[Search] thread = {}, tx active = {}, tx resource = {}", threadName, isActive, txResource);

        var result = searchQueryRepositoryPort.findTopicsByWordWithPageable(word, pageable);

        saveSearchHistory(memberId, word, result);

        return result;
    }

    private void saveSearchHistory(Long memberId, String word, PageCustom<TopicSearchResDTO.Topic> result) {
        Optional.ofNullable(result.content())
                .filter(list -> !list.isEmpty())
                .ifPresentOrElse(
                        content -> topicSearchHistoryCommandRepositoryPort.save(memberId, word, true),
                        () -> topicSearchHistoryCommandRepositoryPort.save(memberId, word, false)
                );
    }

    @Override
    public List<TopicSearchResDTO.Recommendation> recommend() {
        return List.of();
    }
}
