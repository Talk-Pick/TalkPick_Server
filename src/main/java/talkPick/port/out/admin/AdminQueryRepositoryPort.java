package talkPick.port.out.admin;

import talkPick.domain.admin.Admin;

public interface AdminQueryRepositoryPort {
    Admin findAdminById(Long adminId);
}
