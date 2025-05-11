package talkPick.domain.admin.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.domain.admin.adapter.out.dto.AdminResDTO;
import talkPick.domain.admin.domain.Admin;

@Mapper(componentModel = "spring")
public interface AdminResMapper {
    AdminResMapper INSTANCE = Mappers.getMapper(AdminResMapper.class);

    AdminResDTO.Admin toAdmin(Admin admin);

    AdminResDTO.Login toLoginAdmin(Admin admin);
}
