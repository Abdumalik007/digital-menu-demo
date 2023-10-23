package food.system.service.impl;

import food.system.dto.FoodDto;
import food.system.dto.WaiterDto;
import food.system.entity.User;
import food.system.entity.Waiter;
import food.system.mapper.WaiterMapper;
import food.system.repository.WaiterRepository;
import food.system.role.Role;
import food.system.service.main.WaiterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static food.system.helper.ResponseEntityHelper.*;

@RequiredArgsConstructor
@Service
public class WaiterServiceImpl implements WaiterService {
    private final Logger logger = LoggerFactory.getLogger(WaiterService.class);
    private final WaiterRepository waiterRepository;
    private final WaiterMapper waiterMapper;
    private final PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> createWaiter(WaiterDto waiterDto) {
        try {

            if(waiterRepository.existsByUserUsername(waiterDto.getUser().getUsername()))
                return BAD_REQUEST("Waiter with this username already exists");

            Waiter waiter = Waiter.builder().name(waiterDto.getName()).user(
                    User.builder().username(waiterDto.getUser().getUsername())
                            .password(encoder.encode(waiterDto.getUser().getPassword()))
                            .role(Role.WAITER)
                            .build()
            ).build();

            waiterRepository.save(waiter);
            waiterDto.setId(waiter.getId());

            return OK_MESSAGE(waiterDto);
        }catch (Exception e) {
            logger.error("Error while creating waiter: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }

    @Override
    public ResponseEntity<?> updateWaiter(WaiterDto waiterDto) {
        try {
            if(waiterRepository.existsByUserUsernameAndIdIsNot(waiterDto.getUser().getUsername(), waiterDto.getId()))
                return BAD_REQUEST("Waiter with this username already exists");

            Waiter waiter = waiterRepository.findById(waiterDto.getId()).orElseThrow();

            waiter.setName(waiterDto.getName());
            waiter.getUser().setUsername(waiterDto.getUser().getUsername());
            if(waiterDto.getUser().getPassword() != null)
                waiter.getUser().setPassword(encoder.encode(waiterDto.getUser().getPassword()));

            waiterRepository.save(waiter);

            return OK_MESSAGE(waiterDto);
        }catch (Exception e) {
            logger.error("Error while updating waiter: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }

    }

    @Override
    public ResponseEntity<WaiterDto> findWaiterById(Integer id) {
        return waiterRepository.findById(id).map(w -> OK_MESSAGE(waiterMapper.toDto(w)))
                .orElseGet(() -> INTERNAL_ERROR(null));
    }

    @Override
    public ResponseEntity<List<WaiterDto>> findAllWaiters() {
        List<WaiterDto> waiters = waiterRepository.findAll()
                .stream().map(waiterMapper::toDto).toList();
        return OK_MESSAGE(waiters);
    }

    @Override
    public ResponseEntity<List<WaiterDto>> search(String name) {
        List<WaiterDto> foods = waiterRepository.findAllByNameContainsIgnoreCase(name).stream()
                .map(waiterMapper::toDto).toList();
        return ResponseEntity.ok(foods);
    }

    @Override
    public ResponseEntity<?> deleteWaiterById(Integer id) {
        try {
            Waiter waiter = waiterRepository.findById(id).orElseThrow();
            waiterRepository.delete(waiter);
            return OK_MESSAGE("Ok");
        }catch (Exception e) {
            logger.error("Error while deleting waiter: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }
}
