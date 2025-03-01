package talkPick.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.adapter.out.repository.AdminAuthInfoJpaRepository;
import talkPick.adapter.out.repository.AdminJpaRepository;
import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;
import talkPick.error.ErrorCode;
import talkPick.error.exception.AdminException;
import talkPick.port.out.AdminCommandRepositoryPort;

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
    public AdminAuthInfo saveAdminAuthInfo(AdminAuthInfo adminAuthInfo) {
        return adminAuthInfoJpaRepository.save(adminAuthInfo);
    }

    @Override
    public AdminAuthInfo findByPassword(String encryptedPassword) {
        return adminAuthInfoJpaRepository.findByPassword(encryptedPassword)
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_PASSWORD_NOT_FOUND));
    }
}
