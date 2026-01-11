package talkPick.domain.random.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.domain.random.domain.Random;
import java.util.Optional;

public interface RandomJpaRepository extends JpaRepository<Random, Long> {
    Optional<Random> findRandomByMemberIdAndId(Long memberId, Long randomId);

    void deleteByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Random r WHERE r.memberId = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}