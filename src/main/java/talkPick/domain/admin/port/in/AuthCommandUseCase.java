package talkPick.domain.admin.port.in;

import talkPick.domain.admin.adapter.in.dto.AdminReqDTO;
import talkPick.domain.admin.adapter.out.dto.AdminResDTO;
import talkPick.global.security.jwt.dto.JwtResDTO;

public interface AuthCommandUseCase {

    AdminResDTO.Signup signup(AdminReqDTO.Signup signup);

    JwtResDTO.Login login(AdminReqDTO.Login login, JwtResDTO.Login jwtResDTO);
}
