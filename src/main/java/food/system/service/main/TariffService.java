package food.system.service.main;

import food.system.dto.TariffDto;
import org.springframework.http.ResponseEntity;

public interface TariffService {
    ResponseEntity<TariffDto> updateTariff(TariffDto tariffDto);
    ResponseEntity<TariffDto> getTariff();
}
