package talkPick.port.out;

import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;

public interface AdminCommandRepositoryPort {

    Admin saveAdmin(Admin admin);
    Admin findByEmail(String email);
    AdminAuthInfo saveAdminAuthInfo(AdminAuthInfo adminAuthInfo);
    AdminAuthInfo findByPassword(String password);
}
