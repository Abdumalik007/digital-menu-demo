package food.system.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import food.system.role.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(value = {"password", "role", "id"}, allowSetters = true)
public class UserDto implements Serializable {
    private Integer id;
    @NotBlank(message = "Username must not be empty")
    private String username;

    @Size(min = 6, message = "Password must contain at least 6 characters")
    @NotBlank(message = "Password must not be empty")
    private String password;
    private Role role;
}

