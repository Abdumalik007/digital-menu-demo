package food.system.mapper;


import food.system.dto.ReviewDto;
import food.system.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewDto dto);
    ReviewDto toDto(Review review);
}
