package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.global.annotation.UserId;

public interface RandomCommandApi {
    @Operation(summary = "랜덤 토픽 1 : 랜덤 토픽 시작 API", description = "랜덤 토픽 1 : 랜덤 토픽 시작 API 입니다.")
    void start(@UserId final Long memberId);

    @Operation(summary = "랜덤 토픽 3 : 카테고리 선택 API", description = "랜덤 토픽 3 : 카테고리 선택 API 입니다.")
    void selectCategory(@UserId final Long memberId, RandomReqDTO.SelectCategory requestDTO);

    @Operation(summary = "랜덤 토픽 6 : 토픽 선택 API", description = "랜덤 토픽 6 : 토픽 선택 API 입니다.")
    void selectTopic(@UserId final Long memberId, RandomReqDTO.SelectTopic requestDTO);

    @Operation(summary = "랜덤 토픽 7 : 랜덤 토픽 그만하기 API", description = "랜덤 토픽 7 : 랜덤 토픽 그만하기 API 입니다.")
    void quit(@UserId Long memberId, @PathVariable("randomId") Long randomId);

    @Operation(summary = "랜덤 토픽 8 : 랜덤 토픽 종료 및 결과 보기 API", description = "랜덤 토픽 8 : 랜덤 토픽 종료 및 결과 보기 API 입니다.")
    void end(@UserId Long memberId, @PathVariable("randomId") Long randomId);
}