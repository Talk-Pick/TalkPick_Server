package talkPick.domain.inquiry.adapter.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.inquiry.adapter.in.dto.InquiryReqDto;
import talkPick.domain.inquiry.port.in.InquiryCommandUseCase;

@RestController
@RequiredArgsConstructor
public class InquiryCommandController implements InquiryCommandApi{

    private final InquiryCommandUseCase inquiryCommandUseCase;

    @Override
    public void inquirySend(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody InquiryReqDto.inquiryDataRequest request) {
        inquiryCommandUseCase.inquirySend(authorization, request);
    }
}
