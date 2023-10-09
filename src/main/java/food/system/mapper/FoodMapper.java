package food.system.mapper;


import food.system.dto.CategoryDto;
import food.system.dto.FoodDto;
import food.system.entity.Category;
import food.system.entity.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    default CategoryDto categoryEntityToDto(Category entity) {
        return CategoryDto.builder().id(entity.getId()).name(entity.getName()).build();
    }

}
