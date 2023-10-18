package food.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WaiterDto {
    private Integer id;
    @NotBlank(message = "Username must not be empty")
    private String name;
    private UserDto user;
}
