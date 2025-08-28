package talkPick.domain.inquiry.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.inquiry.adapter.in.dto.InquiryReqDto;

@RequestMapping("/api/v1/inquiry")
@Tag(name = "문의 API", description = "문의 관련 API입니다.")
public interface InquiryCommandApi {
    @PostMapping
    @Operation(summary = "문의하기 API", description = "문의 양식에 맞게 정보(문의 유형, 이메일, 내용, 제목)를 입력 하여 문의하는 API입니다.")
    void inquirySend(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody InquiryReqDto.inquiryDataRequest request);
}
