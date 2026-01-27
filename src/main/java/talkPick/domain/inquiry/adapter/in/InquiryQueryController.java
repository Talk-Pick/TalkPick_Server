package talkPick.domain.inquiry.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.domain.inquiry.port.in.InquiryQueryUseCase;
import talkPick.core.common.response.CursorPageResponse;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class InquiryQueryController implements InquiryQueryApi {
    private final InquiryQueryUseCase inquiryQueryUseCase;

    @Override
    public CursorPageResponse<InquiryResDto.InquiryListItemResDto> getMyInquiries(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(value = "cursor", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
            @RequestParam(value = "size", defaultValue = "6") int size
    ) {
        return inquiryQueryUseCase.getMyInquiries(authorization, cursor, size);
    }
}
