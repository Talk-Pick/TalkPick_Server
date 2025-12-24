package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.member.MemberTopicHistory;

public interface MemberTopicHistoryJpaRepository extends JpaRepository<MemberTopicHistory, Long> {
    void deleteByMemberId(Long memberId);
}
