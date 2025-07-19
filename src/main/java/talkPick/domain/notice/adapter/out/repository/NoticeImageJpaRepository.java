package talkPick.domain.notice.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.notice.domain.NoticeImage;

public interface NoticeImageJpaRepository extends JpaRepository<NoticeImage, Long> {
}
