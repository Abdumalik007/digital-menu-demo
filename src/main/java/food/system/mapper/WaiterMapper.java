package food.system.mapper;


import food.system.dto.WaiterDto;
import food.system.entity.Waiter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaiterMapper {
    WaiterDto toDto(Waiter waiter);
}
