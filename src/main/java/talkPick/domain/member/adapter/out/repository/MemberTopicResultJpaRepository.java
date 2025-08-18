package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.member.MemberTopicResult;

public interface MemberTopicResultJpaRepository extends JpaRepository<MemberTopicResult, Long> {
}
