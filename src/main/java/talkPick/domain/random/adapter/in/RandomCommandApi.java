package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.global.security.annotation.UserId;
import java.util.List;

@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 코스 API", description = "랜덤 대화 코스 관련 API 입니다.")
public interface RandomCommandApi {
    @PostMapping("/start")
    @Operation(summary = "랜덤 토픽 1 : 랜덤 토픽 시작 API", description = "랜덤 토픽 1 : 랜덤 토픽 시작 API 입니다.")
    void start(@UserId final Long memberId);

    @PostMapping("/select-category")
    @Operation(summary = "랜덤 토픽 3 : 카테고리 선택 API", description = "랜덤 토픽 3 : 카테고리 선택 API 입니다.")
    List<RandomResDTO.RandomTopic> selectByCategories(@UserId final Long memberId, RandomReqDTO.SelectCategory requestDTO);

    @PostMapping("/select-topic")
    @Operation(summary = "랜덤 토픽 5 : 토픽 선택 API", description = "랜덤 토픽 5 : 토픽 선택 API 입니다.")
    List<RandomResDTO.RandomTopic> selectByTopics(@UserId final Long memberId, RandomReqDTO.SelectTopic requestDTO);

    @PostMapping("/quit/{randomId}")
    @Operation(summary = "랜덤 토픽 6 : 랜덤 토픽 그만하기 API", description = "랜덤 토픽 6 : 랜덤 토픽 그만하기 API 입니다.")
    void quit(@UserId Long memberId, @PathVariable("randomId") Long randomId);

    @PostMapping("/end/{randomId}")
    @Operation(summary = "랜덤 토픽7 : 랜덤 토픽 종료 및 결과 보기 API", description = "랜덤 토픽 7 : 랜덤 토픽 종료 및 결과 보기 API 입니다.")
    RandomResDTO.Result end(@UserId Long memberId, @PathVariable("randomId") Long randomId);

    @PostMapping("/save-result/{randomId}")
    @Operation(summary = "랜덤 토픽 8 : 랜덤 토픽 결과 저장 API", description = "랜덤 토픽 8 : 랜덤 토픽 결과 저장 API 입니다.")
    void saveResult(@UserId Long memberId, @PathVariable("randomId") Long randomId, RandomReqDTO.Result requestDTO);
}