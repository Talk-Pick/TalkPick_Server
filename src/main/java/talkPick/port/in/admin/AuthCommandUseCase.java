package talkPick.port.in.admin;

import talkPick.adapter.in.admin.dto.AdminReqDTO;
import talkPick.adapter.out.admin.dto.AdminResDTO;
import talkPick.common.security.jwt.dto.JwtResDTO;

public interface AuthCommandUseCase {

    AdminResDTO.Signup signup(AdminReqDTO.Signup signup);

    JwtResDTO.Login login(AdminReqDTO.Login login, JwtResDTO.Login jwtResDTO);
}
