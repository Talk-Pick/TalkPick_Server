package talkPick.domain.inquiry.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.inquiry.adapter.out.repository.InquiryJpaRepository;
import talkPick.domain.inquiry.domain.Inquiry;
import talkPick.domain.inquiry.port.out.InquiryCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class InquiryCommandRepositoryAdapter implements InquiryCommandRepositoryPort {
    private final InquiryJpaRepository repository;

    @Override
    public Inquiry save(Inquiry inquiry) {
        return repository.save(inquiry);
    }
}


