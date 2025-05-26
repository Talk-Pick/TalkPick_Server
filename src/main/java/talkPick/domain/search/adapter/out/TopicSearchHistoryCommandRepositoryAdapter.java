package talkPick.domain.search.adapter.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import talkPick.domain.search.adapter.out.repository.TopicSearchHistoryJpaRepository;
import talkPick.domain.search.domain.TopicSearchHistory;
import talkPick.domain.search.port.out.TopicSearchHistoryCommandRepositoryPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicSearchHistoryCommandRepositoryAdapter implements TopicSearchHistoryCommandRepositoryPort {
    private final TopicSearchHistoryJpaRepository topicSearchHistoryJpaRepository;

    @Async
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Long memberId, String keyword, Boolean isResultShown) {
        //TODO 테스트할 때 할 일 : 트랜잭션/스레드 다른지 확인
        String threadName = Thread.currentThread().getName();
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object txResource = TransactionSynchronizationManager.getResource("javax.persistence.EntityManager");
        log.info("[Save] thread = {}, tx active = {}, tx resource = {}", threadName, isActive, txResource);

        topicSearchHistoryJpaRepository.save(TopicSearchHistory.of(memberId, keyword, isResultShown));
    }
}
