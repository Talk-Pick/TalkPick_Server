package talkPick.adapter.in.topic;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.common.annotation.UserId;
import talkPick.common.model.PageCustom;

import java.util.List;

public interface TopicQueryApi {
    @Operation(summary = "상황 별 주제 모아보기 조회 API", description = "상황 별 주제 모아보기 조회 API 입니다.")
    List<TopicResDTO.Categories> getTopCategories();

    @Operation(summary = "카테고리 전체 조회 API", description = "카테고리 전체 조회 API 입니다.")
    PageCustom<TopicResDTO.Categories> getCategories(Pageable pageable);

    @Operation(summary = "오늘의 토픽 5개 조회 API", description = "오늘의 토픽 5개 조회 API 입니다.")
    List<TopicResDTO.TopicSummaries> getTodayTopicSummaries(@UserId Long userId);

    @Operation(summary = "오늘의 토픽 5개 상세 조회 API", description = "오늘의 토픽 5개 상세 조회 API 입니다.")
    List<TopicResDTO.TopicDetails> getTodayTopicDetails(TopicReqDTO.TodayTopics requestDTO);
}
