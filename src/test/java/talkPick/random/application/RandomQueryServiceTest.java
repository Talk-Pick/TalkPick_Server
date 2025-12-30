package talkPick.random.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.application.RandomQueryService;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import talkPick.domain.topic.domain.type.CategoryGroup;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomQueryService 테스트")
class RandomQueryServiceTest {

    @InjectMocks
    private RandomQueryService randomQueryService;

    @Mock
    private RandomQueryRepositoryPort randomQueryRepositoryPort;

    @Test
    @DisplayName("랜덤 토픽 목록 조회 테스트")
    void 랜덤_토픽_목록_조회_테스트() {
        // given
        Long memberId = 1L;
        Long randomId = 100L;
        Integer order = 1;
        CategoryGroup categoryGroup = CategoryGroup.STRANGER;
        String category = "일상";

        List<RandomResDTO.RandomTopic> mockTopics = List.of(
                new RandomResDTO.RandomTopic(1, List.of(
                        new RandomResDTO.RandomTopicDetail(1L, "토픽1", "설명1", "STRANGER", "일상", "키워드1", "img1.png", "icon1.png")
                )),
                new RandomResDTO.RandomTopic(2, List.of(
                        new RandomResDTO.RandomTopicDetail(2L, "토픽2", "설명2", "CLOSE", "대화", "키워드2", "img2.png", "icon2.png")
                ))
        );

        given(randomQueryRepositoryPort.findRandomTopics(memberId, randomId, order, categoryGroup, category))
                .willReturn(mockTopics);

        // when
        List<RandomResDTO.RandomTopic> result = randomQueryService.getRandomTopics(
                memberId, randomId, order, categoryGroup, category
        );

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).getOrder()).isEqualTo(1),
                () -> assertThat(result.get(1).getOrder()).isEqualTo(2),
                () -> verify(randomQueryRepositoryPort, times(1))
                        .findRandomTopics(memberId, randomId, order, categoryGroup, category)
        );
    }

    @Test
    @DisplayName("랜덤 토픽 조회 시 빈 결과 반환 테스트")
    void 랜덤_토픽_조회시_빈_결과_반환_테스트() {
        // given
        Long memberId = 1L;
        Long randomId = 100L;
        Integer order = 1;
        CategoryGroup categoryGroup = CategoryGroup.STRANGER;
        String category = "일상";

        given(randomQueryRepositoryPort.findRandomTopics(memberId, randomId, order, categoryGroup, category))
                .willReturn(Collections.emptyList());

        // when
        List<RandomResDTO.RandomTopic> result = randomQueryService.getRandomTopics(
                memberId, randomId, order, categoryGroup, category
        );

        // then
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result).isEmpty(),
                () -> verify(randomQueryRepositoryPort, times(1))
                        .findRandomTopics(memberId, randomId, order, categoryGroup, category)
        );
    }
}