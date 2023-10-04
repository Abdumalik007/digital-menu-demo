package food.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private String name;
}
