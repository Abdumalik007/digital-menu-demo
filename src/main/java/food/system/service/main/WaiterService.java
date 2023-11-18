package food.system.service.main;

import food.system.dto.WaiterDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WaiterService {
    ResponseEntity<?> createWaiter(WaiterDto waiterDto);
    ResponseEntity<?> updateWaiter(WaiterDto waiterDto);
    ResponseEntity<WaiterDto> findWaiterById(Integer id);
    ResponseEntity<List<WaiterDto>> findAllWaiters();
    ResponseEntity<List<WaiterDto>> search(String name);
    ResponseEntity<?> deleteWaiterById(Integer id);
}
