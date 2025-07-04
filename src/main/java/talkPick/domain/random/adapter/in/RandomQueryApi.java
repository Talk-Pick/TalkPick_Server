package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import java.util.List;

@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 코스 API", description = "랜덤 대화 코스 관련 API 입니다.")
public interface RandomQueryApi {
    @GetMapping("/categories")
    @Operation(summary = "랜덤 토픽 2 : 랜덤 토픽 카테고리 조회 API", description = "랜덤 토픽 2 : 랜덤 토픽 카테고리 조회 API 입니다.")
    List<RandomResDTO.Categories> getCategories();

    @GetMapping("/{topicId}")
    @Operation(summary = "랜덤 토픽 5 : 랜덤 토픽 상세 조회 API", description = "랜덤 토픽 5 : 랜덤 토픽 상세 조회 API 입니다.")
    RandomResDTO.RandomTopicDetail getRandomTopicDetail(@PathVariable("topicId") Long topicId);
}
