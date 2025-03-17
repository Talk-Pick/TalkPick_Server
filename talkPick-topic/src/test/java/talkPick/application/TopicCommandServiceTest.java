package talkPick.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import talkPick.domain.Topic;
import talkPick.exception.AddLikeFailInRedisException;
import talkPick.port.out.TopicCommandRepositoryPort;
import talkPick.port.out.TopicQueryRepositoryPort;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicCommandServiceTest {
    @InjectMocks
    private TopicCommandService topicCommandService;
    @Mock
    private TopicCommandRepositoryPort topicCommandRepositoryPort;
    @Mock
    private TopicQueryRepositoryPort topicQueryRepositoryPort;

    @Test
    @DisplayName("좋아요 기능할 때 Redis INCR이 정상적으로 호출되는지 테스트")
    void 좋아요_기능할_때_Redis_INCR이_정상적으로_호출되는지_테스트() {
        // Given
        Long memberId = 1L;
        Long topicId = 100L;
        Long likeCountInRedis = 10L;

        when(topicCommandRepositoryPort.addLike(memberId, topicId)).thenReturn(likeCountInRedis);
        Topic mockTopic = mock(Topic.class);
        when(topicQueryRepositoryPort.findTopicById(topicId)).thenReturn(mockTopic);

        // When
        topicCommandService.addLike(memberId, topicId);

        // Then
        verify(topicCommandRepositoryPort, times(1)).addLike(memberId, topicId);
        verify(topicQueryRepositoryPort, times(1)).findTopicById(topicId);
        verify(mockTopic, times(1)).addLike(likeCountInRedis);
    }

    @Test
    @DisplayName("Redis 장애 발생할 때 예외가 발생 테스트")
    void Redis_장애_발생할_때_예외가_발생_테스트() {
        // Given
        Long memberId = 1L;
        Long topicId = 100L;

        when(topicCommandRepositoryPort.addLike(memberId, topicId)).thenThrow(new RuntimeException("Redis Error"));

        // When & Then
        assertThrows(AddLikeFailInRedisException.class, () -> topicCommandService.addLike(memberId, topicId));
    }
}