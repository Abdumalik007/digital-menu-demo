package food.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(value = {"category.name", "category.foods"})
@ToString
public class FoodDto {
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    private ImageDto image;
    private String time;
    @NotNull(message = "Category must not be null")
    private CategoryDto category;
    private String composition;
    @NotEmpty
    private List<PortionDto> portions;
    private Boolean status;
}
