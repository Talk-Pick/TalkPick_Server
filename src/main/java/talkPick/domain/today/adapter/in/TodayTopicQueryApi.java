package talkPick.domain.today.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import java.util.List;

@RequestMapping("/api/v1/topic")
@Tag(name = "톡픽 API", description = "톡픽 관련 API 입니다.")
public interface TodayTopicQueryApi {
//    @GetMapping("/today-topic-details")
//    @Operation(summary = "오늘의 토픽 5개 상세 조회 API", description = "오늘의 토픽 5개 상세 조회 API 입니다.")
//    List<TodayTopicResDTO.TopicDetail> getTodayTopicDetails(@RequestParam List<Long> topicIds);
}
