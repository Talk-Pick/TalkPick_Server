package talkPick.port.out;

import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;

public interface AdminCommandRepositoryPort {

    void saveAdmin(Admin admin);
    Admin findByEmail(String email);
    boolean existsByEmail(String email);
    void saveAdminAuthInfo(AdminAuthInfo adminAuthInfo);
    AdminAuthInfo findByPassword(String password);
    AdminAuthInfo findByAdmin(Admin admin);
    void saveAdminWithAuthInfo(Admin admin, AdminAuthInfo adminAuthInfo);
}
