package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.auth.adapter.in.resolver.MemberId;
import java.util.List;

@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 코스 API", description = "랜덤 대화 코스 관련 API 입니다.")
public interface RandomQueryApi {
    @GetMapping("/{id}/topics")
    @Operation(
            summary = "랜덤 대화 주제 코스 조회 API",
            description = """
                          랜덤 대화 주제 코스 조회 API 입니다.
                          order는 현재 순서 기준으로 (1, 2, 3, 4) 넣어주세요.
                          랜덤 대화 주제 코스에서 톡픽들을 조회할 때,
                          해당 API를 한 번 요청해 주세요.
                          랜덤 대화 주제 코스 첫 시도 시, 사용자가 선택한 카테고리를
                          파라미터로 넣어서 요청 주세요.
                          """
    )
    List<RandomResDTO.RandomTopic> getRandomTopics(
            @MemberId @Parameter(hidden = true) Long memberId,
            @PathVariable("id")
            @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42")
            @NotNull(message = "[ERROR] randomId 값이 존재하지 않습니다.") Long randomId,
            @RequestParam(name = "order", required = true)
            @NotNull(message = "[ERROR] order 값이 존재하지 않습니다.") Integer order,
            @RequestParam(name = "category", required = false)
            String category

    );
}
