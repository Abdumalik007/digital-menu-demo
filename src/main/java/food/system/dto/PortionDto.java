package food.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(value = {"food"}, allowSetters = true)
public class PortionDto {
    private Integer id;
    private String unit;
    @Size(min = 0)
    private Integer price;
    private FoodDto food;
}
