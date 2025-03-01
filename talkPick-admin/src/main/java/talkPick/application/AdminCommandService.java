package talkPick.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasypt.encryption.StringEncryptor;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;
import talkPick.error.ErrorCode;
import talkPick.error.exception.AdminException;
import talkPick.mapper.AdminResMapper;
import talkPick.port.in.AdminCommandUseCase;
import talkPick.port.out.AdminCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCommandService implements AdminCommandUseCase {
    private final AdminCommandRepositoryPort adminCommandRepositoryPort;
    private final AdminResMapper adminResMapper;
    @Autowired
    @Qualifier("adminEncryptor")
    private final StringEncryptor adminEncryptor;

    @Override
    public AdminResDTO.Signup signup(AdminReqDTO.Signup signup) {

        // pw 암호화
        String encryptedPassword = adminEncryptor.encrypt(signup.password());
        // Admin 저장
        Admin savedAdmin = saveAdmin(signup.email());
        // AdminAuthInfo 저장
        saveAdminAuthInfo(savedAdmin, encryptedPassword);

        return adminResMapper.toAdmin(savedAdmin);
    }

    private Admin saveAdmin(String email) {
        Admin admin = Admin.toSignupAdminEntity(email);
        // Admin 저장
        return adminCommandRepositoryPort.saveAdmin(admin);
    }

    private void saveAdminAuthInfo(Admin admin, String password) {
        AdminAuthInfo adminAuthInfo = AdminAuthInfo.create(admin, password);
        admin.setAuthInfo(adminAuthInfo);
        adminCommandRepositoryPort.saveAdminAuthInfo(adminAuthInfo);
    }

    @Override
    public AdminResDTO.Login login(AdminReqDTO.Login login) {
        Admin admin = adminCommandRepositoryPort.findByEmail(login.email());

        if (!passwordMatches(login.password(), admin.getPassword())) {
            throw new AdminException(ErrorCode.ADMIN_LOGIN_PASSWORD_FAULT);
        }

        return adminResMapper.toLoginAdmin(admin);
    }

    private void validateAccount(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("비밀번호는 최소 6자 이상이어야 합니다.");
        }
    }

    private boolean passwordMatches(String rawPassword, String encryptedPassword) {
        // 입력된 pw를 암호화
        String encryptedRawPassword = adminEncryptor.encrypt(rawPassword);
        // DB상의 pw와 비교
        return encryptedRawPassword.equals(encryptedPassword);
    }
}
