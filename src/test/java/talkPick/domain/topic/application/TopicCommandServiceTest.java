package talkPick.domain.topic.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import talkPick.domain.topic.domain.event.TopicLikedEvent;
import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("TopicCommandService 테스트")
class TopicCommandServiceTest {

    @InjectMocks
    private TopicCommandService topicCommandService;

    @Mock
    private TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    @DisplayName("토픽 좋아요 추가 및 이벤트 발행 테스트")
    void 토픽_좋아요_추가_및_이벤트_발행_테스트() {
        // given
        Long memberId = 1L;
        Long topicId = 100L;

        willDoNothing().given(topicLikeHistoryCommandRepositoryPort).save(memberId, topicId);
        willDoNothing().given(eventPublisher).publishEvent(any(TopicLikedEvent.class));

        // when
        topicCommandService.addLike(memberId, topicId);

        // then
        verify(topicLikeHistoryCommandRepositoryPort, times(1)).save(memberId, topicId);
        verify(eventPublisher, times(1)).publishEvent(any(TopicLikedEvent.class));
    }

    @Test
    @DisplayName("토픽 좋아요 추가 시 Repository 저장 후 이벤트 발행 순서 확인 테스트")
    void 토픽_좋아요_추가시_Repository_저장_후_이벤트_발행_순서_확인_테스트() {
        // given
        Long memberId = 1L;
        Long topicId = 100L;

        willDoNothing().given(topicLikeHistoryCommandRepositoryPort).save(memberId, topicId);
        willDoNothing().given(eventPublisher).publishEvent(any(TopicLikedEvent.class));

        // when
        topicCommandService.addLike(memberId, topicId);

        // then
        var inOrder = org.mockito.Mockito.inOrder(topicLikeHistoryCommandRepositoryPort, eventPublisher);
        inOrder.verify(topicLikeHistoryCommandRepositoryPort).save(memberId, topicId);
        inOrder.verify(eventPublisher).publishEvent(any(TopicLikedEvent.class));
    }
}