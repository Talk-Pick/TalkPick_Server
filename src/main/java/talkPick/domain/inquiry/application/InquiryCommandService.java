package talkPick.domain.inquiry.application;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.inquiry.adapter.in.dto.InquiryReqDto;
import talkPick.domain.inquiry.adapter.out.repository.InquiryJpaRepository;
import talkPick.domain.inquiry.domain.Inquiry;
import talkPick.domain.inquiry.port.in.InquiryCommandUseCase;
import talkPick.domain.inquiry.converter.InquiryConverter;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.global.security.jwt.util.JwtProvider;

@Service
@RequiredArgsConstructor
public class InquiryCommandService implements InquiryCommandUseCase {

    private final InquiryJpaRepository inquiryJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void inquirySend(String authorization, InquiryReqDto.inquiryDataRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Inquiry inquiry = InquiryConverter.toInquiry(memberId, request);

        inquiryJpaRepository.save(inquiry);

    }


}
