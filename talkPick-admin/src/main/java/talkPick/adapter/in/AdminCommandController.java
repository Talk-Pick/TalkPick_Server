package talkPick.adapter.in;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.port.in.AdminCommandUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminCommandController {
    private final AdminCommandUseCase adminCommandUseCase;

    @PostMapping("/signup")
    public AdminResDTO.Admin adminSignup(@Validated @RequestBody AdminReqDTO.Signup signup) {
        return adminCommandUseCase.signup(signup);
    }

    @PostMapping("/login")
    public void adminLogin(@Validated @RequestBody AdminReqDTO.Login login) {
        adminCommandUseCase.login(login);
    }
}
