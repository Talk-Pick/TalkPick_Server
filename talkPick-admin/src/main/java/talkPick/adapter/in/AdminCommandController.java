package talkPick.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.port.in.AdminCommandUseCase;

import jakarta.validation.Valid;
import talkPick.port.in.AuthCommandUseCase;
import talkPick.security.jwt.dto.JwtResDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminCommandController {
    private final AuthCommandUseCase authCommandUseCase;

    @PostMapping("/signup")
    @Operation(summary = "관리자 회원가입", description = "POST")
    public AdminResDTO.Signup adminSignup(@RequestBody @Valid AdminReqDTO.Signup signup) {
        return authCommandUseCase.signup(signup);
    }

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인", description = "POST")
    public JwtResDTO.Login adminLogin(@RequestBody @Valid AdminReqDTO.Login login, JwtResDTO.Login jwtResDTO) {
        return authCommandUseCase.login(login, jwtResDTO);
    }

    @GetMapping("")
    public AdminResDTO.Test test() {
        return new AdminResDTO.Test("성공!!");
    }
}
