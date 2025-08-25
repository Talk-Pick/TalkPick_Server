package talkPick.domain.random.adapter.in;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/random")
@Tag(name = "랜덤 대화 코스 API", description = "랜덤 대화 코스 관련 API 입니다.")
public interface RandomQueryApi {

}
