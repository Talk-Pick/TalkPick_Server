package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import talkPick.global.annotation.UserId;

public interface RandomCommandApi {
    @Operation(summary = "랜덤 토픽 시작 API", description = "랜덤 토픽 시작 API 입니다.")
    void start(@UserId final Long memberId);
}