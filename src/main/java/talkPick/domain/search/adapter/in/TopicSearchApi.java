package talkPick.domain.search.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.search.adapter.out.dto.TopicSearchResDTO;
import talkPick.global.security.annotation.UserId;
import java.util.List;

public interface TopicSearchApi {
    @Operation(summary = "토픽 필터 조회 API", description = "토픽 필터 조회 API 입니다.")
    List<TopicSearchResDTO.Topic> getTopics(@RequestParam(required = false) String category, Pageable pageable);

    @Operation(summary = "토픽 검색 API", description = "토픽 검색 API API 입니다.")
    List<TopicSearchResDTO.Topic> search(@UserId Long memberId, @RequestParam(required = false) String word);

    @Operation(summary = "추천 검색어 API", description = "추천 검색어 API API 입니다.")
    List<TopicSearchResDTO.Recommendation> recommend();
}