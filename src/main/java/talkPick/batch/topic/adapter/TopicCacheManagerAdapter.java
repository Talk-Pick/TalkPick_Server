package talkPick.batch.topic.adapter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.batch.topic.port.TopicCacheManager;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.external.llm.exception.LLMException;
import talkPick.external.llm.port.LLMClientPort;
import talkPick.batch.topic.exception.JVMCacheException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static talkPick.global.exception.ErrorCode.JVM_CACHE_REFRESH_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicCacheManagerAdapter implements TopicCacheManager {
    private final LLMClientPort llmClientPort;
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;
    private final AtomicReference<List<TopicCacheDTO>> cacheRef = new AtomicReference<>(List.of());

    @PostConstruct
    public void load() {
        refresh();
    }

    @Override
    public List<TopicCacheDTO> getAll() {
        return cacheRef.get();
    }

    //TODO 실패 시 Slack 전송 필요
    @Override
    @Retryable(
            value = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void refresh() {
        log.info("[TopicCache] 캐시 갱신 시작");

        try {
            var newData = topicQueryRepositoryPort.findAllTopicCache();
            cacheRef.set(List.copyOf(newData));

            long usedMB = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
            long freeMB = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            long maxMB = Runtime.getRuntime().maxMemory() / 1024 / 1024;

            log.info("[TopicCache] 캐시 갱신 완료 - 항목 수: {}개 | 사용 메모리: {}MB | 여유 메모리: {}MB | 최대 메모리: {}MB", newData.size(), usedMB, freeMB, maxMB);
            log.info("[TopicCache] 캐시 갱신 시작");

            // TODO 추후 LLM 서버 적용 시, 사용할 예정
//            llmClientPort.send(newData);
        } catch (LLMException e) {
            log.error("[TopicCache] LLM 서버와의 통신 중 오류 발생: {}", e.getMessage());
            throw new JVMCacheException(JVM_CACHE_REFRESH_FAILED, e.getMessage());
        } catch (Exception e) {
            throw new JVMCacheException(JVM_CACHE_REFRESH_FAILED, e.getMessage());
        }
    }

    @Override
    public List<RandomResDTO.RandomTopic> getRandomTopics(final Integer orderId) {
        List<TopicCacheDTO> topicCacheDTOS = cacheRef.get();
        Collections.shuffle(topicCacheDTOS);
        return topicCacheDTOS.stream()
                .limit(4)
                .map(dto -> toRandomTopic(orderId, dto))
                .collect(Collectors.toList());
    }

    private RandomResDTO.RandomTopic toRandomTopic(final Integer order, TopicCacheDTO dto) {
        return RandomResDTO.RandomTopic.of(order, dto);
    }
}