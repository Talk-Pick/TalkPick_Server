package talkPick.domain.inquiry.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.inquiry.domain.Inquiry;

public interface InquiryJpaRepository extends JpaRepository<Inquiry, Long> {
}
