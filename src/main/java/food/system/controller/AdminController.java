package food.system.controller;


import food.system.dto.AdminDto;
import food.system.service.main.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PutMapping
    public ResponseEntity<?> updateAdmin(@ModelAttribute @Valid AdminDto adminDto,
                                         @RequestParam(required = false) MultipartFile file){
        return adminService.updateAdmin(adminDto, file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAdminById(@PathVariable Integer id) {
        return adminService.findAdminById(id);
    }
}

