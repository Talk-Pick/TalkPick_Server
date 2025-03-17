package talkPick.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import talkPick.domain.Topic;
import talkPick.port.out.TopicCommandRepositoryPort;
import talkPick.port.out.TopicQueryRepositoryPort;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicCommandServiceConcurrencyTest {
    @InjectMocks
    private TopicCommandService topicCommandService;
    @Mock
    private TopicCommandRepositoryPort topicCommandRepositoryPort;
    @Mock
    private TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Test
    @DisplayName("좋아요 기능 동시성 테스트")
    void 좋아요_기능_동시성_테스트() throws InterruptedException {
        // Given
        Long topicId = 100L;
        AtomicLong redisLikeCounter = new AtomicLong(0);

        // When
        when(topicCommandRepositoryPort.addLike(anyLong(), anyLong()))
                .thenAnswer(invocation -> redisLikeCounter.incrementAndGet());

        Topic mockTopic = mock(Topic.class);
        when(topicQueryRepositoryPort.findTopicById(topicId)).thenReturn(mockTopic);

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    topicCommandService.addLike((long) finalI, topicId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // Then
        assertEquals(100, redisLikeCounter.get());
        verify(topicCommandRepositoryPort, times(100)).addLike(anyLong(), anyLong());
    }
}