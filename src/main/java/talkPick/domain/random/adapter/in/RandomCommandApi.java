package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.global.security.annotation.MemberId;

@Validated
@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 주제 코스 API", description = "랜덤 대화 주제 코스 관련 API 입니다.")
public interface RandomCommandApi {
    @PostMapping("/start")
    @Operation(summary = "랜덤 대화 주제 코스 시작 API", description = "랜덤 대화 주제 코스 시작 API 입니다. 랜덤 대화 주제 코스를 시작하게 될 때, 해당 API를 한 번 요청해 주세요.")
    void start(@MemberId @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long memberId);

    @PostMapping("/{id}/quit")
    @Operation(summary = "랜덤 대화 주제 코스 그만하기 API", description = "랜덤 대화 주제 코스 그만하기 API 입니다. 랜덤 대화 주제 코스를 중간에 그만둘 때, 해당 API를 한 번 요청해 주세요.")
    void quit(@MemberId @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") Long memberId, @PathVariable("id") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") Long randomId);

    @PostMapping("/{id}/end")
    @Operation(summary = "랜덤 대화 주제 코스 종료 API", description = "랜덤 대화 주제 코스 종료 API 입니다. 랜덤 대화 주제 코스를 종료할 때, 해당 API를 한 번 요청해 주세요.")
    void end(@MemberId @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") Long memberId, @PathVariable("id") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") Long randomId);
}