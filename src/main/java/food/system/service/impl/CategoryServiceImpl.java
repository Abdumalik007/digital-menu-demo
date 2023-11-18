package food.system.service.impl;

import food.system.dto.CategoryDto;
import food.system.entity.Category;
import food.system.entity.Food;
import food.system.entity.Image;
import food.system.mapper.FoodMapper;
import food.system.repository.CategoryRepository;
import food.system.repository.FoodRepository;
import food.system.service.main.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.*;
import static food.system.util.ImageUtil.IMAGE_PATH;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    public static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;


    @Override
    public ResponseEntity<CategoryDto> createCategory(String name) {
        try {
            if(categoryRepository.existsByName(name))
                return INTERNAL_ERROR(null);
            Category category = Category.builder().name(name).build();

            category = categoryRepository.save(category);
            CategoryDto categoryDto = CategoryDto.builder()
                    .id(category.getId()).name(category.getName()).build();

            return OK_MESSAGE(categoryDto);
        }catch (Exception e) {
            logger.error("Error while creating category: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(String name, Integer id) {
        try {
            if(categoryRepository.existsByNameAndIdIsNot(name, id))
                return INTERNAL_ERROR(null);
            System.out.println("All: " + categoryRepository.findAll());
            Category category = categoryRepository.findById(id).orElseThrow();
            category.setName(name);
            categoryRepository.save(category);

            CategoryDto dto = CategoryDto.builder().id(id).name(name).build();
            return ResponseEntity.ok(dto);
        }catch (Exception e) {
            logger.error("Error while updating category: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }


    @Override
    public ResponseEntity<CategoryDto> findCategoryById(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty())
            return NOT_FOUND(null);
        CategoryDto categoryDto = CategoryDto.builder()
                .id(categoryOptional.get().getId())
                .name(categoryOptional.get().getName())
                .build();
        return ResponseEntity.ok(categoryDto);
    }


    @Override
    public ResponseEntity<?> deleteCategoryById(Integer id) {
        try {
            List<Food> foods = foodRepository.findAllByCategoryIdOrderById(id);
            categoryRepository.deleteById(id);
            for (Food food : foods) {
                Image image = food.getImage();
                foodRepository.deleteById(food.getId());
                Files.delete(Path.of(IMAGE_PATH + File.separator + image.getPath()));
            }
            return OK_MESSAGE("OK");
        }catch (Exception e) {
            logger.error("Error while deleting category: ".concat(e.getMessage()));
            return INTERNAL_ERROR(null);
        }
    }


    @Override
    public ResponseEntity<List<CategoryDto>> search(String name) {
        List<CategoryDto> categoryDtoList =
                categoryRepository.findAllByNameContainsIgnoreCase(name).stream()
                        .map(c -> CategoryDto.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .build())
                        .toList();
        return ResponseEntity.ok(categoryDtoList);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtoList =
                categoryRepository.findAll().stream()
                        .map(c -> CategoryDto.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .build())
                        .toList();
        return ResponseEntity.ok(categoryDtoList);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategoriesWithFood() {
        List<CategoryDto> categoryDtoList =
                categoryRepository.findAllByOrderByFoodsPortionsId().stream()
                        .map(c -> CategoryDto.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .foods(
                                        c.getFoods() != null ?
                                                c.getFoods().stream().map(foodMapper::toDto).toList() :
                                                null
                                )
                                .build())
                        .toList();
        return ResponseEntity.ok(categoryDtoList);
    }


}
