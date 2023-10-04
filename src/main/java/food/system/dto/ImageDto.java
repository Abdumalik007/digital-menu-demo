package food.system.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageDto {
    private Integer id;
    private String name;
    private String path;
    private String ext;
}
