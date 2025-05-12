package talkPick.domain.member.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.member.domain.profile.Profile;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
}
