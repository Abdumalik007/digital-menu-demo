package food.system.service.main;

import food.system.dto.AdminDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    ResponseEntity<AdminDto> updateAdmin(AdminDto adminDto, MultipartFile file);
    ResponseEntity<AdminDto> findAdminById(Integer id);
}
