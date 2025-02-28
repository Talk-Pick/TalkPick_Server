package talkPick.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.domain.Admin;
import talkPick.mapper.AdminResMapper;
import talkPick.port.in.AdminCommandUseCase;
import talkPick.port.out.AdminCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminCommandService implements AdminCommandUseCase {
    private final AdminCommandRepositoryPort adminCommandRepositoryPort;
    private final AdminResMapper adminResMapper;

    @Override
    public AdminResDTO.Signup signup(AdminReqDTO.Signup signup) {
        Admin admin = saveAdmin(signup.email(), signup.password());
        return adminResMapper.toAdmin(admin);
    }

    @Override
    public AdminResDTO.Login login(AdminReqDTO.Login login) {
        Admin admin = adminCommandRepositoryPort.findByEmail(login.email());

        if (!passwordMatches(login.password(), admin.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return adminResMapper.toLoginAdmin(admin);
    }

    private Admin saveAdmin(String email, String password) {
        return adminCommandRepositoryPort.saveAdmin(Admin.toSignupAdminEntity(email, password));
    }

    private void validateAccount(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("비밀번호는 최소 6자 이상이어야 합니다.");
        }
    }

    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword); // 실제로는 BCryptPasswordEncoder를 사용해야 함
    }
}
