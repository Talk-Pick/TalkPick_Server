package talkPick.port.out;

import talkPick.domain.Admin;

public interface AdminCommandRepositoryPort {

    Admin saveAdmin(Admin admin);
    Admin findByEmail(String email);
}
