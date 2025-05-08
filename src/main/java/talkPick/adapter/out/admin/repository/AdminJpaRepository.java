package talkPick.adapter.out.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.admin.Admin;

import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);
}
