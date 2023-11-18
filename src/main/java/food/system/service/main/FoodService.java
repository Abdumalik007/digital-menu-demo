package food.system.service.main;

import food.system.dto.FoodDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {
    ResponseEntity<FoodDto> createFood(FoodDto foodDto, MultipartFile file);
    ResponseEntity<FoodDto> updateFood(FoodDto categoryDto, MultipartFile file);
    ResponseEntity<FoodDto> findFoodById(Integer id);
    ResponseEntity<List<FoodDto>> findFoodByStatus(String status);
    ResponseEntity<?> deleteFoodById(Integer id);
    ResponseEntity<List<FoodDto>> findFoodByCategoryId(Integer id);
    ResponseEntity<List<FoodDto>> search(String name);
    ResponseEntity<List<FoodDto>> getAllFoods();
    ResponseEntity<FoodDto> changeStatus(Integer id, Boolean status);
}
