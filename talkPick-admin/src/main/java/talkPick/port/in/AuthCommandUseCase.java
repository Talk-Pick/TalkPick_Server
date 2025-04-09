package talkPick.port.in;

import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.security.jwt.dto.JwtResDTO;

public interface AuthCommandUseCase {

    AdminResDTO.Signup signup(AdminReqDTO.Signup signup);

    JwtResDTO.Login login(AdminReqDTO.Login login, JwtResDTO.Login jwtResDTO);
}
