package food.system.dto;

import food.system.entity.Food;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private List<FoodDto> foods;
}
