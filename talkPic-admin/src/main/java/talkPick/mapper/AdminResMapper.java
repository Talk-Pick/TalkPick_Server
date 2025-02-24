package talkPick.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.adapter.out.dto.AdminResDTO;
import talkPick.domain.Admin;

@Mapper(componentModel = "spring")
public interface AdminResMapper {
    AdminResMapper INSTANCE = Mappers.getMapper(AdminResMapper.class);

    AdminResDTO.Signup toAdmin(Admin admin);

    AdminResDTO.Login toLoginAdmin(Admin admin);
}
