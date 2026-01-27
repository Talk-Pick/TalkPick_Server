package talkPick.domain.today.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.global.security.resolver.MemberId;
import java.util.List;

@Validated
@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TodayTopicQueryApi {
    @GetMapping("/today-topics")
    @Operation(summary = "오늘의 토픽 5개 조회 API", description = "오늘의 토픽 5개 조회 API 입니다.")
    List<TodayTopicResDTO.TodayTopic> getTodayTopics(@MemberId @Parameter(hidden = true) final Long memberId);
}