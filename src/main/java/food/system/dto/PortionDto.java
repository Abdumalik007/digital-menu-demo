package food.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(value = {"food"}, allowSetters = true)
@ToString
public class PortionDto {
    private Integer id;
    @NotBlank(message = "Unit must not be blank")
    private String unit;
    @NotBlank(message = "Price must not be blank")
    private Integer price;
    private FoodDto food;
}
