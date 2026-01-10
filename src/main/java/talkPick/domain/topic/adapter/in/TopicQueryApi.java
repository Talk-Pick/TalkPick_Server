package talkPick.domain.topic.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import java.util.List;

@Validated
@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TopicQueryApi {
    @GetMapping("/categories")
    @Operation(summary = "카테고리 전체 조회 API", description = "카테고리 전체 조회 API 입니다. 기존에 CategoryGroup을 파라미터로 넣어서 보냈는데, 이제 안 넣으시고 요청하셔도 됩니다.")
    List<TopicResDTO.Categories> getCategories();

    @GetMapping("/{topicId}")
    @Operation(summary = "토픽 상세 조회 API", description = "토픽 상세 조회 API 입니다.")
    TopicResDTO.TopicDetail getTopicDetail(@PathVariable("topicId") @Parameter(description = "토픽 고유 ID(topicId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long topicId);
}