package talkPick.domain.inquiry.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import talkPick.domain.inquiry.adapter.in.dto.InquiryReqDto;
import talkPick.domain.inquiry.domain.Inquiry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InquiryConverter {

    public static Inquiry toInquiry(Long memberId, InquiryReqDto.inquiryDataRequest request) {
        return Inquiry.builder()
                .memberId(memberId)
                .title(request.getTitle())
                .content(request.getContent())
                .email(request.getEmail())
                .type(request.getType())
                .isAnswered(false)
                .build();
    }
}
