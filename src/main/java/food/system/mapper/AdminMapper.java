package food.system.mapper;

import food.system.dto.AdminDto;
import food.system.entity.Admin;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminDto toDto(Admin admin);
}
