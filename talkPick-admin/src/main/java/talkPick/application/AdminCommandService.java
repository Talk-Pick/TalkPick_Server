package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasypt.encryption.StringEncryptor;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.adapter.out.mapper.AdminResMapper;
import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;
import talkPick.error.ErrorCode;
import talkPick.error.exception.AdminException;
import talkPick.port.in.AdminCommandUseCase;
import talkPick.port.out.AdminCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCommandService implements AdminCommandUseCase {
    private final AdminCommandRepositoryPort adminCommandRepositoryPort;
    private final AdminResMapper adminResMapper;
    private final StringEncryptor adminEncryptor;

    @Override
    public AdminResDTO.Admin signup(AdminReqDTO.Signup signup) {

        String encryptedPassword = adminEncryptor.encrypt(signup.password());
        Admin savedAdmin = createAdmin(signup.email(), encryptedPassword);

        return adminResMapper.toAdmin(savedAdmin);
    }

    @Override
    public AdminResDTO.Login login(AdminReqDTO.Login login) {

        Admin admin = adminCommandRepositoryPort.findByEmail(login.email());
        AdminAuthInfo adminAuthInfo = adminCommandRepositoryPort.findByAdmin(admin);

        passwordMatches(login.password(), adminAuthInfo.getPassword());

        return adminResMapper.toLoginAdmin(admin);
    }

    private Admin createAdmin(String email, String password) {

        Admin admin = Admin.create(email);
        AdminAuthInfo adminAuthInfo = AdminAuthInfo.create(admin, password);
        admin.updateAuthInfo(adminAuthInfo);

        adminCommandRepositoryPort.saveAdminWithAuthInfo(admin, adminAuthInfo);
        return adminCommandRepositoryPort.findByEmail(email);
    }

    public void passwordMatches(String rawPassword, String encryptedPassword) {

        String decryptedPassword = adminEncryptor.decrypt(encryptedPassword);

        if (!rawPassword.equals(decryptedPassword)) {
            throw new AdminException(ErrorCode.ADMIN_LOGIN_PASSWORD_FAULT);
        }
    }
}
