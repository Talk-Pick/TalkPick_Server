package talkPick.adapter.out.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.admin.Admin;
import talkPick.domain.admin.AdminAuthInfo;

import java.util.Optional;

public interface AdminAuthInfoJpaRepository extends JpaRepository<AdminAuthInfo, Long> {

    Optional<AdminAuthInfo> findByPassword(String password);
    Optional<AdminAuthInfo> findByAdmin(Admin admin);
}
