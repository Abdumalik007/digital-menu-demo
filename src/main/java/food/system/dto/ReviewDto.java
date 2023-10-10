package food.system.dto;


import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDto {
    private Integer id;
    private String name;
    private String text;
    private LocalDate createdAt;
}
