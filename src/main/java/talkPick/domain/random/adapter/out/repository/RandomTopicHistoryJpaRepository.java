package talkPick.domain.random.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.random.domain.RandomTopicHistory;

import java.util.Optional;

public interface RandomTopicHistoryJpaRepository extends JpaRepository<RandomTopicHistory, Long> {
    Optional<RandomTopicHistory> findByMemberIdAndRandomIdAndOrder(Long memberId, Long randomId, Integer order);
}