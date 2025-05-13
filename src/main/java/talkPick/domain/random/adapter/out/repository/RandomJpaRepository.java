package talkPick.domain.random.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.random.domain.Random;
import java.util.Optional;

public interface RandomJpaRepository extends JpaRepository<Random, Long> {
    Optional<Random> findRandomByMemberIdAndId(Long memberId, Long randomId);
}
