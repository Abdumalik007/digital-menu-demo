package food.system.service.impl;

import food.system.dto.FoodDto;
import food.system.dto.PortionDto;
import food.system.entity.Category;
import food.system.entity.Food;
import food.system.entity.Image;
import food.system.entity.Portion;
import food.system.mapper.FoodMapper;
import food.system.repository.FoodRepository;
import food.system.repository.ImageRepository;
import food.system.service.main.FoodService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.*;
import static food.system.util.ImageUtil.buildImage;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    public static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<FoodDto> createFood(FoodDto foodDto, MultipartFile file) {
        try {
            if(foodRepository.existsByName(foodDto.getName()))
                return INTERNAL_ERROR(null);
            Food food = foodMapper.toEntity(foodDto);
            food.setStatus(true);
            food.setImage(buildImage(file));
            food.getPortions().forEach(p -> p.setFood(food));
            foodRepository.save(food);
            foodDto.setId(food.getId());

            return OK_MESSAGE(foodDto);
        }catch (Exception e) {
            logger.error("Error while creating food: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }

    @Override
    public ResponseEntity<FoodDto> updateFood(FoodDto foodDto, MultipartFile file) {
        try {
            if(foodRepository.existsByNameAndIdIsNot(foodDto.getName(), foodDto.getId()))
                return INTERNAL_ERROR(null);

            Food food = foodRepository.findById(foodDto.getId()).orElseThrow();
            updateFoodProperties(food, foodDto);
            if(file != null)
                updateFoodImage(food, file);

            foodRepository.save(food);

            return OK_MESSAGE(foodDto);
        }catch (Exception e) {
            logger.error("Error while updating food: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }




    @Override
    public ResponseEntity<FoodDto> findFoodById(Integer id) {
        return foodRepository.findById(id).map(f -> ResponseEntity.ok(foodMapper.toDto(f)))
                .orElseGet(() -> NOT_FOUND(null));
    }


    @Override
    public ResponseEntity<?> deleteFoodById(Integer id) {
        try {
            Food food = foodRepository.findById(id).orElseThrow();
            Image image = food.getImage();
            foodRepository.deleteById(id);
            Files.delete(Path.of("uploads/" + image.getPath()));
            return OK_MESSAGE("Ok");
        }catch (Exception e) {
            logger.error("Error while deleting food: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }

    @Override
    public ResponseEntity<List<FoodDto>> findFoodByCategoryId(Integer id) {
        List<FoodDto> foods = foodRepository.findAllByCategoryIdOrderById(id).stream()
                .map(foodMapper::toDto).toList();
        return ResponseEntity.ok(foods);
    }


    @Override
    public ResponseEntity<List<FoodDto>> search(String name) {
        List<FoodDto> foods = foodRepository.findAllByNameContainsIgnoreCase(name).stream()
                .map(foodMapper::toDto).toList();
        return ResponseEntity.ok(foods);
    }

    @Override
    public ResponseEntity<List<FoodDto>> getAllFoods() {
        List<FoodDto> foods = foodRepository.findAllByOrderById().stream()
                .map(foodMapper::toDto).toList();
        return ResponseEntity.ok(foods);
    }

    @Override
    public ResponseEntity<FoodDto> changeStatus(Integer id, Boolean status) {
        Optional<Food> optional = foodRepository.findById(id);
        if(optional.isEmpty()) return NOT_FOUND(null);

        Food food = optional.get();
        food.setStatus(status);
        foodRepository.save(food);

        FoodDto dto = foodMapper.toDto(optional.get());
        return ResponseEntity.ok(dto);
    }


    private void updateFoodProperties(Food food, FoodDto foodDto) {
        food.setName(foodDto.getName());
        food.setTime(foodDto.getTime());
        food.setComposition(foodDto.getComposition());
        updatePortions(food.getPortions(), foodDto.getPortions());
        food.setCategory(Category.builder().id(foodDto.getCategory().getId()).build());
    }

    private void updatePortions(List<Portion> oldPortions, List<PortionDto> newPortions) {
        for (int i = 0; i < 4; i++) {
            PortionDto newPortion = newPortions.get(i);
            updateEachPortion(oldPortions.get(i), newPortion);
        }
    }

    private void updateEachPortion(Portion oldPortion, PortionDto newPortion) {
        oldPortion.setUnit(newPortion.getUnit());
        oldPortion.setPrice(newPortion.getPrice());
    }

    private void updateFoodImage(Food food, MultipartFile file) throws IOException {
        Image image = food.getImage();
        System.out.println(image);
        imageRepository.deleteById(image.getId());
        Files.delete(Path.of("uploads/" + image.getPath()));
        food.setImage(buildImage(file));
    }
}
