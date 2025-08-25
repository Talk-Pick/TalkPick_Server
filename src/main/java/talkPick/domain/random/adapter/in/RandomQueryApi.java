package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.global.security.annotation.MemberId;
import java.util.List;

@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 코스 API", description = "랜덤 대화 코스 관련 API 입니다.")
public interface RandomQueryApi {
    @GetMapping("/{id}/topics")
    @Operation(summary = "랜덤 대화 주제 코스 조회 API", description = "랜덤 대화 주제 코스 조회 API 입니다. 랜덤 대화 주제 코스에서 톡픽들을 조회할 때, 해당 API를 한 번 요청해 주세요.")
    List<RandomResDTO.RandomTopic> getRandomTopics(
            @MemberId
            @NotNull(message = "[ERROR] memberId 값이 존재하지 않습니다.") Long memberId,
            @PathVariable("id")
            @NotNull(message = "[ERROR] randomId 값이 존재하지 않습니다.") Long randomId,
            @RequestParam(name = "order", required = true)
            @NotNull(message = "[ERROR] order 값이 존재하지 않습니다.") Integer order
    );
}
