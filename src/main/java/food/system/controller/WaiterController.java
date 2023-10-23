package food.system.controller;

import food.system.dto.WaiterDto;
import food.system.service.main.WaiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waiter")
public class WaiterController {

    private final WaiterService waiterService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createWaiter(@RequestBody @Valid WaiterDto waiterDto) {
        return waiterService.createWaiter(waiterDto);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateWaiter(@RequestBody @Valid WaiterDto waiterDto) {
        return waiterService.updateWaiter(waiterDto);
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchWaiter(@PathVariable String name) {
        return waiterService.search(name);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findWaiterById(@PathVariable Integer id) {
        return waiterService.findWaiterById(id);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findAllWaiters() {
        return waiterService.findAllWaiters();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteWaiterById(@PathVariable Integer id) {
        return waiterService.deleteWaiterById(id);
    }

}
