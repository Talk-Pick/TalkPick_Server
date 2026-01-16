package talkPick.topic.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import talkPick.domain.topic.application.TopicCommandService;
import talkPick.domain.topic.domain.TopicLikeHistory;
import talkPick.domain.topic.domain.event.TopicLikedEvent;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import talkPick.domain.topic.port.out.TopicLikeHistoryQueryRepositoryPort;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("TopicCommandService 테스트")
class TopicCommandServiceTest {

    @InjectMocks
    private TopicCommandService topicCommandService;

    @Mock
    private TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;

    @Mock
    private TopicLikeHistoryQueryRepositoryPort topicLikeHistoryQueryRepositoryPort;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    @DisplayName("최초 좋아요 시 기록이 없으면 신규 저장하고 이벤트를 발행한다")
    void 최초_좋아요_테스트() {
        // given
        Long memberId = 1L;
        Long topicId = 100L;

        when(topicLikeHistoryQueryRepositoryPort.findActiveHistory(memberId, topicId))
                .thenReturn(Optional.empty());

        // when
        topicCommandService.toggleLike(memberId, topicId);

        // then
        verify(topicLikeHistoryCommandRepositoryPort, times(1)).save(memberId, topicId);
        verify(eventPublisher, times(1)).publishEvent(any(TopicLikedEvent.class));
        verify(topicLikeHistoryCommandRepositoryPort, never()).delete(any());
    }

    @Test
    @DisplayName("이미 좋아요가 되어 있으면 기록을 비활성화(삭제)하고 이벤트를 발행하지 않는다")
    void 좋아요_취소_테스트() {
        // given
        Long memberId = 1L;
        Long topicId = 100L;
        TopicLikeHistory existingHistory = spy(TopicLikeHistory.of(memberId, topicId));

        // ACTIVE한 기록이 이미 존재하는 상황 가정
        when(topicLikeHistoryQueryRepositoryPort.findActiveHistory(memberId, topicId))
                .thenReturn(Optional.of(existingHistory));

        // when
        topicCommandService.toggleLike(memberId, topicId);

        // then
        verify(topicLikeHistoryCommandRepositoryPort, times(1)).delete(existingHistory);
        verify(topicLikeHistoryCommandRepositoryPort, never()).save(anyLong(), anyLong());
        verify(eventPublisher, never()).publishEvent(any(TopicLikedEvent.class));
    }

    @Test
    @DisplayName("좋아요 시 ArgumentCaptor로 발행된 이벤트의 필드를 검증한다")
    void 이벤트_내용_검증_테스트() {
        // given
        Long memberId = 1L;
        Long topicId = 100L;
        ArgumentCaptor<TopicLikedEvent> eventCaptor = ArgumentCaptor.forClass(TopicLikedEvent.class);

        when(topicLikeHistoryQueryRepositoryPort.findActiveHistory(memberId, topicId))
                .thenReturn(Optional.empty());

        // when
        topicCommandService.toggleLike(memberId, topicId);

        // then
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        TopicLikedEvent capturedEvent = eventCaptor.getValue();

        assertAll(
                () -> assertThat(capturedEvent.getMemberId()).isEqualTo(memberId),
                () -> assertThat(capturedEvent.getTopicId()).isEqualTo(topicId)
        );
    }
}