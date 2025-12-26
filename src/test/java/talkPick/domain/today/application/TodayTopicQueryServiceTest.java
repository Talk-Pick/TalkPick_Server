package talkPick.domain.today.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.domain.event.TodayTopicSavedEvent;
import talkPick.domain.today.port.out.TodayTopicQueryRepositoryPort;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodayTopicQueryService 테스트")
class TodayTopicQueryServiceTest {

    @InjectMocks
    private TodayTopicQueryService todayTopicQueryService;

    @Mock
    private TodayTopicQueryRepositoryPort todayTopicQueryRepositoryPort;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Test
    @DisplayName("오늘의 토픽 조회 시 캐시 미존재로 DB 조회 및 이벤트 발행 테스트")
    void 오늘의_토픽_조회시_캐시_미존재로_DB_조회_및_이벤트_발행_테스트() {
        // given
        Long memberId = 1L;
        List<TodayTopicResDTO.TodayTopic> mockTopics = List.of(
                new TodayTopicResDTO.TodayTopic(100L, "토픽1", "카테고리1", "키워드1", "icon1.png"),
                new TodayTopicResDTO.TodayTopic(200L, "토픽2", "카테고리2", "키워드2", "icon2.png")
        );

        given(cacheManager.getCache("todayTopics")).willReturn(cache);
        given(cache.get(memberId)).willReturn(null);
        given(todayTopicQueryRepositoryPort.findTodayTopics(memberId)).willReturn(mockTopics);
        willDoNothing().given(cache).put(eq(memberId), any());
        willDoNothing().given(eventPublisher).publishEvent(any(TodayTopicSavedEvent.class));

        // when
        List<TodayTopicResDTO.TodayTopic> result = todayTopicQueryService.getTodayTopics(memberId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result).hasSize(2),
                () -> verify(todayTopicQueryRepositoryPort, times(1)).findTodayTopics(memberId),
                () -> verify(cache, times(1)).put(eq(memberId), any()),
                () -> verify(eventPublisher, times(1)).publishEvent(any(TodayTopicSavedEvent.class))
        );
    }

    @Test
    @DisplayName("오늘의 토픽 조회 시 빈 결과 반환 테스트")
    void 오늘의_토픽_조회시_빈_결과_반환_테스트() {
        // given
        Long memberId = 1L;
        List<TodayTopicResDTO.TodayTopic> emptyList = Collections.emptyList();

        given(cacheManager.getCache("todayTopics")).willReturn(cache);
        given(cache.get(memberId)).willReturn(null);
        given(todayTopicQueryRepositoryPort.findTodayTopics(memberId)).willReturn(emptyList);
        willDoNothing().given(cache).put(eq(memberId), any());
        willDoNothing().given(eventPublisher).publishEvent(any(TodayTopicSavedEvent.class));

        // when
        List<TodayTopicResDTO.TodayTopic> result = todayTopicQueryService.getTodayTopics(memberId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result).isEmpty(),
                () -> verify(todayTopicQueryRepositoryPort, times(1)).findTodayTopics(memberId),
                () -> verify(eventPublisher, times(1)).publishEvent(any(TodayTopicSavedEvent.class))
        );
    }

    @Test
    @DisplayName("오늘의 토픽 조회 시 캐시 매니저 null인 경우 정상 조회 테스트")
    void 오늘의_토픽_조회시_캐시_매니저_null인_경우_정상_조회_테스트() {
        // given
        Long memberId = 1L;
        List<TodayTopicResDTO.TodayTopic> mockTopics = List.of(
                new TodayTopicResDTO.TodayTopic(100L, "토픽1", "카테고리1", "키워드1", "icon1.png")
        );

        given(cacheManager.getCache("todayTopics")).willReturn(null);
        given(todayTopicQueryRepositoryPort.findTodayTopics(memberId)).willReturn(mockTopics);
        willDoNothing().given(eventPublisher).publishEvent(any(TodayTopicSavedEvent.class));

        // when
        List<TodayTopicResDTO.TodayTopic> result = todayTopicQueryService.getTodayTopics(memberId);

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result).hasSize(1),
                () -> verify(todayTopicQueryRepositoryPort, times(1)).findTodayTopics(memberId),
                () -> verify(eventPublisher, times(1)).publishEvent(any(TodayTopicSavedEvent.class))
        );
    }
}