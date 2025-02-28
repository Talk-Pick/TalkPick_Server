package talkPick.port.in;

import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;

public interface AdminCommandUseCase {

    AdminResDTO.Signup signup(AdminReqDTO.Signup signup);

    AdminResDTO.Login login(AdminReqDTO.Login login);
}
