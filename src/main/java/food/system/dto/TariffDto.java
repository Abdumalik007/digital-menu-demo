package food.system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TariffDto {
    private Integer id;
    private Integer standard;
    private Integer vipPercent;
    private Integer vipSum;
}
