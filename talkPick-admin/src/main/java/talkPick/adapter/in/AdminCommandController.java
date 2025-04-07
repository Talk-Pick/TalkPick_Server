package talkPick.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.port.in.AdminCommandUseCase;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminCommandController {
    private final AdminCommandUseCase adminCommandUseCase;

    @PostMapping("/signup")
    public AdminResDTO.Admin adminSignup(@RequestBody @Valid AdminReqDTO.Signup signup) {
        return adminCommandUseCase.signup(signup);
    }

    @PostMapping("/login")
    public void adminLogin(@Validated @RequestBody AdminReqDTO.Login login) {
        adminCommandUseCase.login(login);
    }
}
