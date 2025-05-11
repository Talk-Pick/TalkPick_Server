package talkPick.domain.admin.port.out;

import talkPick.domain.admin.domain.Admin;

public interface AdminQueryRepositoryPort {
    Admin findAdminById(Long adminId);
}
