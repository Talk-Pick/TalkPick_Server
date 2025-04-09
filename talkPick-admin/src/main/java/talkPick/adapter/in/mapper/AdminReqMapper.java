package talkPick.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.domain.Admin;

@Mapper(componentModel = "spring")
public interface AdminReqMapper {

    AdminReqMapper INSTANCE = Mappers.getMapper(AdminReqMapper.class);

    AdminReqDTO.Admin toAdmin(Admin admin);

    AdminResDTO.Signup toSignupRes(Admin admin);
}