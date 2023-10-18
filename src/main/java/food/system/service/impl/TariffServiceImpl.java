package food.system.service.impl;

import food.system.dto.TariffDto;
import food.system.entity.Admin;
import food.system.entity.Tariff;
import food.system.entity.User;
import food.system.repository.TariffRepository;
import food.system.role.Role;
import food.system.service.main.TariffService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static food.system.helper.ResponseEntityHelper.INTERNAL_ERROR;
import static food.system.helper.ResponseEntityHelper.OK_MESSAGE;


@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {
    public static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final TariffRepository tariffRepository;


    @PostConstruct
    public void init() {
        if(tariffRepository.count() == 0) {
            Tariff tariff = Tariff.builder()
                            .standard(0)
                            .vipPercent(0)
                            .vipSum(0)
                            .build();
            tariffRepository.save(tariff);
        }
    }


    @Override
    public ResponseEntity<TariffDto> updateTariff(TariffDto tariffDto) {
        try {
            Tariff tariff = tariffRepository.findById(tariffDto.getId()).orElseThrow();

            tariff.setStandard(tariffDto.getStandard());
            tariff.setVipPercent(tariffDto.getVipPercent());
            tariff.setVipSum(tariffDto.getVipSum());

            return OK_MESSAGE(tariffDto);
        }catch (Exception e) {
            logger.error("Error while updating tariff");
            return INTERNAL_ERROR(null);
        }
    }

    @Override
    public ResponseEntity<TariffDto> getTariff() {
        Tariff tariff = tariffRepository.findById(0).orElseThrow();
        TariffDto tariffDto = TariffDto.builder()
                        .id(tariff.getId())
                        .standard(tariff.getStandard())
                        .vipPercent(tariff.getVipPercent())
                        .vipSum(tariff.getVipSum())
                    .build();
        return OK_MESSAGE(tariffDto);
    }
}
