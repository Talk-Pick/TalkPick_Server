package talkPick.domain.auth.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.admin.adapter.in.dto.AdminReqDTO;
import talkPick.domain.admin.adapter.in.mapper.AdminReqMapper;
import talkPick.domain.admin.adapter.out.dto.AdminResDTO;
import talkPick.domain.admin.domain.Admin;
import talkPick.domain.admin.domain.AdminAuthInfo;
import talkPick.global.exception.ErrorCode;
import talkPick.domain.admin.exception.AdminException;
import talkPick.domain.auth.Role;
import talkPick.domain.admin.port.in.AuthCommandUseCase;
import talkPick.domain.admin.port.out.AdminCommandRepositoryPort;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtProvider;

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
        Admin admin = createAdmin(signup);

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

    private Admin createAdmin(AdminReqDTO.Signup signup) {

        Admin admin = Admin.create(signup.email(), signup.name());
        AdminAuthInfo adminAuthInfo = AdminAuthInfo.create(admin, signup.password());

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
