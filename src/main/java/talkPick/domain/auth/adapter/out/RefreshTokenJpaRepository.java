package talkPick.domain.auth.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import talkPick.domain.auth.domain.RefreshToken;
import talkPick.domain.member.domain.Member;

import java.util.Optional;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByMember(Member member);

    Optional<RefreshToken> findByMemberId(Long memberId);

    void deleteByMember(Member member);

    void deleteByToken(String token);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM RefreshToken r WHERE r.member.id = :memberId")
    void deleteAllByMemberIdInBulk(@Param("memberId") Long memberId);
}