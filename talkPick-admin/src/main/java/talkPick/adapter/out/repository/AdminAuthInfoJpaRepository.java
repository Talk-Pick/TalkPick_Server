package talkPick.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;

import java.util.Optional;

public interface AdminAuthInfoJpaRepository extends JpaRepository<AdminAuthInfo, Long> {

    Optional<AdminAuthInfo> findByPassword(String password);
    Optional<AdminAuthInfo> findByAdmin(Admin admin);
}
