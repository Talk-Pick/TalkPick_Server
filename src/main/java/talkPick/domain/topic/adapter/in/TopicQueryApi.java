package talkPick.domain.topic.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.global.security.annotation.UserId;
import java.util.List;

@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TopicQueryApi {
    @GetMapping("/total-categories")
    @Operation(summary = "카테고리 전체 조회 API", description = "카테고리 전체 조회 API 입니다.")
    Slice<TopicResDTO.Categories> getCategories(Pageable pageable);

    @GetMapping("/today-topics")
    @Operation(summary = "오늘의 토픽 5개 조회 API", description = "오늘의 토픽 5개 조회 API 입니다.")
    List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(@UserId Long userId);

    @GetMapping("/today-topic-details")
    @Operation(summary = "오늘의 토픽 5개 상세 조회 API", description = "오늘의 토픽 5개 상세 조회 API 입니다.")
    List<TopicResDTO.TopicDetail> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);

    @GetMapping("/{topicId}")
    @Operation(summary = "토픽 상세 조회 API", description = "토픽 상세 조회 API 입니다.")
    TopicResDTO.TopicDetail getTopicDetail(@PathVariable("topicId") Long topicId);
}