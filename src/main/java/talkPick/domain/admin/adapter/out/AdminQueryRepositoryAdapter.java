package talkPick.domain.admin.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.admin.adapter.out.repository.AdminJpaRepository;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.admin.AdminException;
import talkPick.domain.admin.domain.Admin;
import talkPick.domain.admin.port.out.AdminQueryRepositoryPort;

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
