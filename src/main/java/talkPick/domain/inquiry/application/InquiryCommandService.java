package talkPick.domain.inquiry.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.inquiry.adapter.in.dto.InquiryReqDto;
import talkPick.domain.inquiry.port.out.InquiryCommandRepositoryPort;
import talkPick.domain.inquiry.domain.Inquiry;
import talkPick.domain.inquiry.port.in.InquiryCommandUseCase;
import talkPick.domain.inquiry.converter.InquiryConverter;
import talkPick.global.security.jwt.util.JwtProvider;

@Service
@RequiredArgsConstructor
public class InquiryCommandService implements InquiryCommandUseCase {

    private final InquiryCommandRepositoryPort inquiryCommandRepositoryPort;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void inquirySend(String authorization, InquiryReqDto.inquiryDataRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Inquiry inquiry = InquiryConverter.toInquiry(memberId, request);

        inquiryCommandRepositoryPort.save(inquiry);

    }


}
