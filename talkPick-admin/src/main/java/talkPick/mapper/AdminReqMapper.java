package talkPick.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.adapter.in.dto.AdminReqDTO;
import talkPick.domain.Admin;

@Mapper(componentModel = "spring")
public interface AdminReqMapper {

    AdminReqMapper INSTANCE = Mappers.getMapper(AdminReqMapper.class);

    AdminReqDTO.Signup toAdmin(Admin admin);
}