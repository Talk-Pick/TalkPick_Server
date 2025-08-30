package talkPick.domain.inquiry.port.out;

import talkPick.domain.inquiry.domain.Inquiry;

public interface InquiryCommandRepositoryPort {
    Inquiry save(Inquiry inquiry);
}


