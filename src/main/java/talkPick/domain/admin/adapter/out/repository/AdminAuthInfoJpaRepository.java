package talkPick.domain.admin.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.admin.domain.Admin;
import talkPick.domain.admin.domain.AdminAuthInfo;

import java.util.Optional;

public interface AdminAuthInfoJpaRepository extends JpaRepository<AdminAuthInfo, Long> {

    Optional<AdminAuthInfo> findByPassword(String password);
    Optional<AdminAuthInfo> findByAdmin(Admin admin);
}
