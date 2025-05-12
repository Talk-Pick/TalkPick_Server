package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import java.util.List;

public interface RandomQueryApi {
    @Operation(summary = "랜덤 토픽 2 : 랜덤 토픽 카테고리 조회 API", description = "랜덤 토픽 2 : 랜덤 토픽 카테고리 조회 API 입니다.")
    List<RandomResDTO.Categories> getCategories();
}
