package talkPick.domain.topic.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.global.security.annotation.UserId;

@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TopicCommandApi {
    @PostMapping("/{topicId}/like")
    @Operation(summary = "토픽 좋아요 API", description = "토픽 좋아요 API 입니다.")
    void addLike(@UserId final Long memberId, @PathVariable("topicId") final Long topicId);
}
