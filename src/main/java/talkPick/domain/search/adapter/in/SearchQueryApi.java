package talkPick.domain.search.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.global.common.model.PageCustom;
import java.util.List;

public interface SearchQueryApi {
    @Operation(summary = "토픽 필터 조회 API", description = "토픽 필터 조회 API 입니다.")
    public List<SearchResDTO.Topic> getTopics(@RequestParam(required = false) String category, Pageable pageable);

    @Operation(summary = "토픽 검색 API", description = "토픽 검색 API API 입니다.")
    public PageCustom<SearchResDTO.Topic> search(@RequestParam(required = false) String word, Pageable pageable);
}