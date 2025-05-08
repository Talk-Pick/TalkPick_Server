package talkPick.adapter.in.admin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.adapter.in.admin.dto.AdminReqDTO;
import talkPick.adapter.out.admin.dto.AdminResDTO;
import talkPick.domain.admin.Admin;

@Mapper(componentModel = "spring")
public interface AdminReqMapper {

    AdminReqMapper INSTANCE = Mappers.getMapper(AdminReqMapper.class);

    AdminReqDTO.Admin toAdmin(Admin admin);

    AdminResDTO.Signup toSignupRes(Admin admin);
}