package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.member.MemberTopicResult;

import java.util.Optional;

public interface MemberTopicResultJpaRepository extends JpaRepository<MemberTopicResult, Long> {
    Optional<MemberTopicResult> findByMemberTopicHistoryId(Long memberTopicHistoryId);
}
