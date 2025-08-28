package talkPick.domain.inquiry.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.domain.inquiry.adapter.out.repository.InquiryQuerydslRepository;
import talkPick.domain.inquiry.port.out.InquiryQueryRepositoryPort;
import talkPick.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InquiryQueryRepositoryAdapter implements InquiryQueryRepositoryPort {
    private final InquiryQuerydslRepository repository;

    @Override
    public List<InquiryResDto.InquiryListItemResDto> findMyInquiries(Member member, LocalDateTime cursor, int size) {
        return repository.findMyInquiries(member, cursor, size);
    }
}


