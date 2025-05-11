package talkPick.adapter.out.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.adapter.out.admin.repository.AdminJpaRepository;
import talkPick.common.error.ErrorCode;
import talkPick.common.error.exception.AdminException;
import talkPick.domain.admin.Admin;
import talkPick.port.out.admin.AdminQueryRepositoryPort;

@Component
@RequiredArgsConstructor
public class AdminQueryRepositoryAdapter implements AdminQueryRepositoryPort {

    private final AdminJpaRepository adminJpaRepository;

    @Override
    public Admin findAdminById(Long adminId) {
        return adminJpaRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_NOT_FOUND));
    }
}
