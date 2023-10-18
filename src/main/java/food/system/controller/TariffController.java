package food.system.controller;


import food.system.dto.TariffDto;
import food.system.service.main.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tariff")
public class TariffController {

    private final TariffService tariffService;

    @PutMapping
    public ResponseEntity<TariffDto> updateTariff(@RequestBody TariffDto tariffDto) {
        return tariffService.updateTariff(tariffDto);
    }


    @GetMapping
    public ResponseEntity<TariffDto> getTariff() {
        return tariffService.getTariff();
    }

}
