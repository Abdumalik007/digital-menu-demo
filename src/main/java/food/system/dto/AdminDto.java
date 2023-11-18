package food.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminDto {
    private Integer id;
    @NotBlank(message = "Firstname must not be blank")
    private String name;
    @NotNull(message = "Password and username are required!")
    private UserDto user;
    private ImageDto image;
}
