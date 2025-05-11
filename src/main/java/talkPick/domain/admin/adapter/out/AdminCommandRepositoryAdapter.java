package talkPick.domain.admin.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.admin.adapter.out.repository.AdminAuthInfoJpaRepository;
import talkPick.domain.admin.adapter.out.repository.AdminJpaRepository;
import talkPick.domain.admin.domain.Admin;
import talkPick.domain.admin.domain.AdminAuthInfo;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.admin.AdminException;
import talkPick.domain.admin.port.out.AdminCommandRepositoryPort;

@Component
@RequiredArgsConstructor
public class AdminCommandRepositoryAdapter implements AdminCommandRepositoryPort {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminAuthInfoJpaRepository adminAuthInfoJpaRepository;

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminJpaRepository.save(admin);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminJpaRepository.findByEmail(email)
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_EMAIL_NOT_FOUND));
    }

    @Override
    public boolean existsByEmail(String email) {
        return adminJpaRepository.existsByEmail(email);
    }

    @Override
    public void saveAdminAuthInfo(AdminAuthInfo adminAuthInfo) {
        adminAuthInfoJpaRepository.save(adminAuthInfo);
    }

    @Override
    public AdminAuthInfo findByPassword(String encryptedPassword) {
        return adminAuthInfoJpaRepository.findByPassword(encryptedPassword)
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_PASSWORD_NOT_FOUND));
    }

    @Override
    public AdminAuthInfo findByAdmin(Admin admin) {
        return adminAuthInfoJpaRepository.findByAdmin(admin)
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_NOT_FOUND));
    }

    @Override
    public void saveAdminWithAuthInfo(Admin admin, AdminAuthInfo adminAuthInfo) {
        saveAdmin(admin);
        saveAdminAuthInfo(adminAuthInfo);
    }
}
