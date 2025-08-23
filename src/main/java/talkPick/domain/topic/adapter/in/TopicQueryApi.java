package talkPick.domain.topic.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;

@Validated
@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TopicQueryApi {
    @GetMapping("/total-categories")
    @Operation(summary = "카테고리 전체 조회 API", description = "카테고리 전체 조회 API 입니다.")
    Slice<TopicResDTO.Categories> getCategories(Pageable pageable);

    @GetMapping("/{topicId}")
    @Operation(summary = "토픽 상세 조회 API", description = "토픽 상세 조회 API 입니다.")
    TopicResDTO.TopicDetail getTopicDetail(@PathVariable("topicId") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long topicId);
}