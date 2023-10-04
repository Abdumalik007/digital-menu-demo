package food.system.service.main;

import food.system.dto.AdminDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
    ResponseEntity<?> updateAdmin(AdminDto adminDto, MultipartFile file);
    ResponseEntity<?> findAdminById(Integer id);
}
