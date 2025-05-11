package talkPick.domain.admin.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.domain.admin.adapter.in.dto.AdminReqDTO;
import talkPick.domain.admin.adapter.out.dto.AdminResDTO;
import talkPick.domain.admin.domain.Admin;

@Mapper(componentModel = "spring")
public interface AdminReqMapper {

    AdminReqMapper INSTANCE = Mappers.getMapper(AdminReqMapper.class);

    AdminReqDTO.Admin toAdmin(Admin admin);

    AdminResDTO.Signup toSignupRes(Admin admin);
}