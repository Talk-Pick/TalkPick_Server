package talkPick.adaptor.in;

import io.swagger.v3.oas.annotations.Operation;
import talkPick.adaptor.out.dto.TopicResDTO;

public interface TopicQueryApi {
    @Operation(summary = "상황 별 주제 모아보기 조회 API", description = "상황 별 주제 모아보기 조회 API 입니다.")
    TopicResDTO.Categories getCategories();
}
