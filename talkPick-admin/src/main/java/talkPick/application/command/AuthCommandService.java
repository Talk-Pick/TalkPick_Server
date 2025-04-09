package talkPick.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.in.mapper.AdminReqMapper;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.domain.Admin;
import talkPick.domain.AdminAuthInfo;
import talkPick.domain.type.Role;
import talkPick.error.ErrorCode;
import talkPick.error.exception.AdminException;
import talkPick.port.in.AuthCommandUseCase;
import talkPick.port.out.AdminCommandRepositoryPort;
import talkPick.security.jwt.dto.JwtResDTO;
import talkPick.security.jwt.util.JwtProvider;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthCommandService implements AuthCommandUseCase {
    private final AdminCommandRepositoryPort adminCommandRepositoryPort;
    private final JwtProvider jwtProvider;
    private final AdminReqMapper adminReqMapper;

    @Override
    public AdminResDTO.Signup signup(AdminReqDTO.Signup signup) {

        validateEmailDuplication(signup.email());
        Admin admin = createAdmin(signup.email(), signup.password());

        return adminReqMapper.toSignupRes(admin);
    }

    @Override
    public JwtResDTO.Login login(AdminReqDTO.Login login, JwtResDTO.Login jwtResDTO) {
        Admin admin = adminCommandRepositoryPort.findByEmail(login.email());
        AdminAuthInfo adminAuthInfo = adminCommandRepositoryPort.findByAdmin(admin);

        // 비밀번호 검증
        if (!adminAuthInfo.isMatchedPassword(login.password())) {
            throw new AdminException(ErrorCode.ADMIN_LOGIN_PASSWORD_FAULT);
        }

        return getToken(admin.getId());
    }

    private Admin createAdmin(String email, String password) {

        Admin admin = Admin.create(email);
        AdminAuthInfo adminAuthInfo = AdminAuthInfo.create(admin, password);

        Admin newAdmin = adminCommandRepositoryPort.saveAdmin(admin);
        adminCommandRepositoryPort.saveAdminAuthInfo(adminAuthInfo);
        admin.updateAuthInfo(adminAuthInfo);

        return newAdmin;
    }

    public void validateEmailDuplication(String email) {
        if (adminCommandRepositoryPort.existsByEmail(email)) {
            throw new AdminException(ErrorCode.ADMIN_EMAIL_ALREADY_EXISTS);
        }
    }

    private JwtResDTO.Login getToken(final Long userId) {
        return jwtProvider.createJwt(userId, String.valueOf(Role.ADMIN));
    }
}
