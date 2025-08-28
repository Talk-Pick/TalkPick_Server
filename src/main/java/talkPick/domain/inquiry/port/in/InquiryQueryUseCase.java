package talkPick.domain.inquiry.port.in;

import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.global.response.CursorPageResponse;

import java.time.LocalDateTime;

public interface InquiryQueryUseCase {
    CursorPageResponse<InquiryResDto.InquiryListItemResDto> getMyInquiries(String authorization, LocalDateTime cursor, int size);
}
