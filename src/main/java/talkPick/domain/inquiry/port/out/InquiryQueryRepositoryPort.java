package talkPick.domain.inquiry.port.out;

import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface InquiryQueryRepositoryPort {
    List<InquiryResDto.InquiryListItemResDto> findMyInquiries(Member member, LocalDateTime cursor, int size);
}


