package talkPick.domain.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.domain.type.Keyword;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicQuerydslRepository;
import talkPick.global.exception.ErrorCode;
import talkPick.domain.topic.exception.TopicNotFoundException;
import java.util.*;

import static talkPick.global.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdapter implements TopicQueryRepositoryPort {
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public Topic findTopicById(final Long topicId) {
        return topicJpaRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(TOPIC_NOT_FOUND));
    }

    @Override
    public Slice<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        return topicQuerydslRepository.findCategoriesWithPageable(pageable);
    }

    @Override
    public List<TopicResDTO.TopicSummaries> findTodayTopicSummaries(Long userId) {
        //TODO 수정해야 함.
        return List.of(
                new TopicResDTO.TopicSummaries(
                        1L,
                        "첫 만남에 어색함을 깨는 방법은?",
                        "https://dummyimage.com/600x400/111/fff&text=그룹모임",
                        150L,
                        42,
                        "그룹 첫 모임",
                        CategoryGroup.STRANGER,
                        Keyword.GAME
                ),
                new TopicResDTO.TopicSummaries(
                        2L,
                        "내 룸메이트와의 첫 대화는?",
                        "https://dummyimage.com/600x400/222/fff&text=룸메",
                        130L,
                        37,
                        "룸메 첫 만남",
                        CategoryGroup.STRANGER,
                        Keyword.DAILY_LIFE
                ),
                new TopicResDTO.TopicSummaries(
                        3L,
                        "가족들과 떠났던 가장 기억에 남는 여행은?",
                        "https://dummyimage.com/600x400/444/fff&text=가족",
                        170L,
                        55,
                        "가족",
                        CategoryGroup.CLOSE,
                        Keyword.TRAVEL
                ),
                new TopicResDTO.TopicSummaries(
                        4L,
                        "친구와의 우정 테스트! 너라면?",
                        "https://dummyimage.com/600x400/555/fff&text=친구",
                        110L,
                        28,
                        "친구",
                        CategoryGroup.CLOSE,
                        Keyword.WHAT_IF
                ),
                new TopicResDTO.TopicSummaries(
                        5L,
                        "요즘 빠진 음식은?",
                        "https://dummyimage.com/600x400/666/fff&text=연인",
                        90L,
                        18,
                        "연인",
                        CategoryGroup.CLOSE,
                        Keyword.TASTE
                )
        );
    }

    @Override
    public List<TopicResDTO.TopicDetail> findTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO) {
        return topicQuerydslRepository.findTopicDetailsByIds(requestDTO);
    }

    @Override
    public TopicResDTO.TopicDetail findTopicDetail(Long topicId) {
        return Optional.ofNullable(topicQuerydslRepository.findTopicDetailById(topicId))
                .orElseThrow(() -> new TopicNotFoundException(TOPIC_NOT_FOUND));
    }

    @Override
    public List<TopicCacheDTO> findAllTopicCache() {
        return topicQuerydslRepository.findAllTopicData();
    }
}