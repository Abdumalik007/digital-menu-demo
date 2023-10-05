package food.system.service.impl;

import food.system.dto.CategoryDto;
import food.system.entity.Category;
import food.system.repository.CategoryRepository;
import food.system.service.main.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.*;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    public static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<?> createCategory(String name) {
        try {
            if(categoryRepository.existsByName(name))
                return INTERNAL_ERROR();
            Category category = Category.builder().name(name).build();
            categoryRepository.save(category);
            return OK_MESSAGE();
        }catch (Exception e) {
            logger.error("Error while creating category: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> updateCategory(CategoryDto categoryDto) {
        try {
            if(categoryRepository.existsByNameAndIdIsNot(categoryDto.getName(), categoryDto.getId()))
                return INTERNAL_ERROR();
            Category category = Category.builder()
                                .id(categoryDto.getId())
                                .name(categoryDto.getName())
                                .build();
            categoryRepository.save(category);
            return OK_MESSAGE();
        }catch (Exception e) {
            logger.error("Error while updating category: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findCategoryById(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty())
            return NOT_FOUND();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(categoryOptional.get().getId())
                .name(categoryOptional.get().getName())
                .build();
        return ResponseEntity.ok(categoryDto);
    }

    @Override
    public ResponseEntity<?> deleteCategoryById(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return OK_MESSAGE();
        }catch (Exception e) {
            logger.error("Error while deleting category: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> search(String name) {
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
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDto> categoryDtoList =
                categoryRepository.findAll().stream()
                        .map(c -> CategoryDto.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .build()).toList();
        return ResponseEntity.ok(categoryDtoList);
    }


}
