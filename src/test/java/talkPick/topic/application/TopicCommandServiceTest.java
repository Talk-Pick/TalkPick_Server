//package talkPick.topic.application;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import talkPick.domain.topic.application.TopicCommandService;
//import talkPick.domain.topic.domain.TopicStat;
//import talkPick.domain.topic.port.out.TopicLikeHistoryCommandRepositoryPort;
//import talkPick.domain.topic.port.out.TopicStatQueryRepositoryPort;
//import talkPick.global.exception.ErrorCode;
//import talkPick.global.exception.handler.TopicExceptionHandler;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TopicCommandServiceTest {
//    @InjectMocks
//    private TopicCommandService topicCommandService;
//    @Mock
//    private TopicStatQueryRepositoryPort topicStatQueryRepositoryPort;
//    @Mock
//    private TopicLikeHistoryCommandRepositoryPort topicLikeHistoryCommandRepositoryPort;
//
//    @Test
//    @DisplayName("✅ 좋아요 성공 테스트")
//    void 좋아요_성공_테스트() {
//        // given
//        Long memberId = 1L;
//        Long topicId = 100L;
//        TopicStat mockTopicStat = mock(TopicStat.class);
//
//        given(topicStatQueryRepositoryPort.findTopicStatByTopicId(topicId)).willReturn(mockTopicStat);
//
//        // when
//        topicCommandService.addLike(memberId, topicId);
//
//        // then
//        verify(mockTopicStat).addLike();
//        verify(topicLikeHistoryCommandRepositoryPort).save(memberId, topicId);
//    }
//
////    @Test
////    @DisplayName("🚨 없는 TopicId로 좋아요 실패 테스트")
////    void 없는_TopicId로_좋아요_실패_테스트() {
////        // given
////        Long memberId = 1L;
////        Long topicId = 100L;
////
////        given(topicStatQueryRepositoryPort.findTopicStatByTopicId(topicId)).willThrow(new TopicExceptionHandler(ErrorCode.TOPIC_NOT_FOUND));
////
////        // when && then
////        assertThrows(TopicExceptionHandler.class, () -> topicCommandService.addLike(memberId, topicId));
////    }
//}