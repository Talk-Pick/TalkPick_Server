package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
