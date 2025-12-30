package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.global.model.TalkPickStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderId(String sub);
    Optional<Member> findByEmail(String email);
    Optional<Member> findTop1ByStatusAndDeletedAtBefore(TalkPickStatus status, LocalDateTime dateTime);
    List<Member> findByStatusAndDeletedAtBefore(TalkPickStatus status, LocalDateTime dateTime);
}
