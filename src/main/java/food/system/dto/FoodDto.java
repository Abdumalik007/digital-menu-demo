package food.system.dto;

import food.system.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FoodDto {
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotNull(message = "Image must not be null")
    private ImageDto image;
    private String time;
    @NotNull(message = "Category must not be null")
    private CategoryDto category;
    private String composition;
    @NotNull(message = "Price must not be null")
    private Integer price;
    private Boolean status;
}
