package food.system.service.main;

import food.system.dto.FoodDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {
    ResponseEntity<?> createFood(FoodDto foodDto, MultipartFile file);
    ResponseEntity<?> updateFood(FoodDto categoryDto, MultipartFile file);
    ResponseEntity<?> findFoodById(Integer id);
    ResponseEntity<?> deleteFoodById(Integer id);
    ResponseEntity<?> findFoodByCategoryId(Integer id);
    ResponseEntity<?> search(String name);
    ResponseEntity<?> getAllFoods();
    ResponseEntity<?> changeStatus(Integer id, Boolean status);
}
