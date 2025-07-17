package talkPick.domain.search.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.global.security.annotation.UserId;
import java.util.List;

@RequestMapping("/api/v1/search")
@Tag(name = "톡픽 검색 API", description = "톡픽 검색 관련 API 입니다.")
public interface TopicSearchApi {
    @GetMapping("/topics")
    @Operation(summary = "토픽 필터 조회 API", description = "토픽 필터 조회 API 입니다.")
    List<TopicSearchResDTO.Topic> getTopics(@RequestParam(required = false) String category);

    @GetMapping("/")
    @Operation(summary = "토픽 검색 API", description = "토픽 검색 API API 입니다.")
    List<TopicSearchResDTO.Topic> search(@UserId Long memberId, @RequestParam(required = false) String word);

    @GetMapping("/recommendations")
    @Operation(summary = "추천 검색어 API", description = "추천 검색어 API API 입니다.")
    List<TopicSearchResDTO.Recommendation> recommend();
}