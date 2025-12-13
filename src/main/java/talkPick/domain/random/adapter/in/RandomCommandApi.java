package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.global.security.annotation.MemberId;

@Validated
@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 주제 코스 API", description = "랜덤 대화 주제 코스 관련 API 입니다.")
public interface RandomCommandApi {
    @PostMapping("/start")
    @Operation(summary = "랜덤 대화 주제 코스 시작 API", description = "랜덤 대화 주제 코스 시작 API 입니다. 랜덤 대화 주제 코스를 시작하게 될 때, 해당 API를 한 번 요청해 주세요.")
    RandomResDTO.RandomStart start(@MemberId @Parameter(hidden = true) final Long memberId);

//    @PostMapping("/{id}/next")
    @Operation(summary = "랜덤 대화 주제 코스 다음 API", description = "랜덤 대화 주제 코스 다음 API 입니다. 랜덤 대화 주제 코스에서 다음으로 넘어갈 때마다, 해당 API를 한 번 요청해 주세요.")
    void next(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId, @RequestBody RandomReqDTO.Next requestDTO);

    @PostMapping("/{id}/quit")
    @Operation(summary = "랜덤 대화 주제 코스 그만하기 API", description = "랜덤 대화 주제 코스 그만하기 API 입니다. 랜덤 대화 주제 코스를 중간에 그만둘 때, 해당 API를 한 번 요청해 주세요.")
    void quit(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId);

    @PostMapping("/{id}/end")
    @Operation(summary = "랜덤 대화 주제 코스 종료 API", description = "랜덤 대화 주제 코스 종료 API 입니다. 랜덤 대화 주제 코스를 종료할 때, 해당 API를 한 번 요청해 주세요.")
    void end(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId);

    @PostMapping("{id}/total-record")
    @Operation(summary = "랜덤 대화 주제 코스 기록 API", description = "랜덤 대화 주제 코스 동안 프론트에서 기록 저장 후, 마지막에 보내주세요. 만약에 중간에 그만둘 경우에도 요청 주세요.")
    void totalRecord(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId, @RequestBody RandomReqDTO.TotalRecords requestDTO);


    //    @PostMapping("/{id}/record")
    @Operation(summary = "랜덤 대화 주제 코스 기록 API", description = "랜덤 대화 주제 코스 기록 API 입니다. 톡픽 선택할 때마다 기록을 위해, 해당 API를 한 번 요청해 주세요.")
    void record(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId, @RequestBody RandomReqDTO.Record requestDTO);

    @PostMapping("/{id}/rate")
    @Operation(summary = "랜덤 대화 주제 코스 평점 저장 API", description = "랜덤 대화 주제 코스 평점 저장 API 입니다.")
    void rate(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId, @RequestBody RandomReqDTO.Rate requestDTO);

    @PostMapping("/{id}/comment")
    @Operation(summary = "랜덤 대화 주제 코스 한 줄 평 저장 API", description = "랜덤 대화 주제 코스 한 줄 평 저장 API 입니다.")
    void comment(@MemberId @Parameter(hidden = true) final Long memberId, @PathVariable("id") @Parameter(description = "랜덤 대화 코스 고유 ID(randomId)", example = "42") @NotNull(message = "[ERROR] id 값이 존재하지 않습니다.") final Long randomId, @RequestBody RandomReqDTO.Comment requestDTO);
}