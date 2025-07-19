package talkPick.domain.notice.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.notice.domain.Notice;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {
}
