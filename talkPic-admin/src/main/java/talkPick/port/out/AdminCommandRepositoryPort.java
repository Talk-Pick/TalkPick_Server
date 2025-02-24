package talkPick.port.out;

import talkPick.domain.Admin;

public interface AdminCommandRepositoryPort {

    Admin saveAdmin(Admin admin);
    Admin findByAdminId(String adminCode);
    Admin findByEmail(String email);
    boolean existByAdminCode(String adminCode);
}
