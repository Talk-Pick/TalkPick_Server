package talkPick.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import talkPick.annotation.UserId;

public interface TopicCommandApi {
    @Operation(summary = "토픽 좋아요 API", description = "토픽 좋아요 API 입니다.")
    void addLike(@UserId final Long memberId, @PathVariable("topicId") final Long topicId);
}
