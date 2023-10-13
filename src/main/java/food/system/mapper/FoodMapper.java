package food.system.mapper;


import food.system.dto.CategoryDto;
import food.system.dto.FoodDto;
import food.system.dto.PortionDto;
import food.system.entity.Category;
import food.system.entity.Food;
import food.system.entity.Portion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    @Mapping(target = "category")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "status", ignore = true)
    Food toEntity(FoodDto dto);
    @Mapping(target = "category")
    FoodDto toDto(Food food);

    default Category categoryDtoToEntity(CategoryDto dto) {
        return Category.builder().id(dto.getId()).name(dto.getName()).build();
    }

    default List<PortionDto> portionEntityToDto(List<Portion> portions) {
        return portions != null ?
                portions.stream().map(p ->
                        PortionDto.builder().
                                id(p.getId()).
                                unit(p.getUnit()).
                                price(p.getPrice())
                                .build()
                ).collect(Collectors.toList()) : null;
    }

    default CategoryDto categoryEntityToDto(Category entity) {
        return CategoryDto.builder().id(entity.getId()).name(entity.getName()).build();
    }

}
