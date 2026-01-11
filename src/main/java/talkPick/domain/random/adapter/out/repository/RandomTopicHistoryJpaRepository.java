package talkPick.domain.random.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.random.domain.RandomTopicHistory;

import java.util.Optional;

public interface RandomTopicHistoryJpaRepository extends JpaRepository<RandomTopicHistory, Long> {
    Optional<RandomTopicHistory> findByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, Integer order);

    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM RandomTopicHistory r WHERE r.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}
