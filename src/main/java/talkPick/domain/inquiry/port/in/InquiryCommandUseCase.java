package talkPick.domain.inquiry.port.in;

import talkPick.domain.inquiry.adapter.in.dto.InquiryReqDto;

public interface InquiryCommandUseCase {
    void inquirySend(String authorization, InquiryReqDto.inquiryDataRequest request);
}
