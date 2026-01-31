package talkPick.domain.topic.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.auth.adapter.in.resolver.MemberId;

@Validated
@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TopicCommandApi {
    @PostMapping("/{topicId}/like")
    @Operation(summary = "토픽 좋아요 API", description = "토픽 좋아요 API 입니다.")
    void addLike(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("topicId") @Parameter(description = "토픽 고유 ID(topicId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long topicId);
}