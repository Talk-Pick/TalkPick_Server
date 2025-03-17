package talkPick.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
