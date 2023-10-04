package food.system.mapper;

import food.system.dto.AdminDto;
import food.system.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "user.role")
    AdminDto toDto(Admin admin);
    Admin toEntity(AdminDto adminDto);
}
