package talkPick.domain.admin.port.out;

import talkPick.domain.admin.domain.Admin;
import talkPick.domain.admin.domain.AdminAuthInfo;

public interface AdminCommandRepositoryPort {
    Admin saveAdmin(Admin admin);
    Admin findByEmail(String email);
    boolean existsByEmail(String email);
    void saveAdminAuthInfo(AdminAuthInfo adminAuthInfo);
    AdminAuthInfo findByPassword(String password);
    AdminAuthInfo findByAdmin(Admin admin);
    void saveAdminWithAuthInfo(Admin admin, AdminAuthInfo adminAuthInfo);
}
