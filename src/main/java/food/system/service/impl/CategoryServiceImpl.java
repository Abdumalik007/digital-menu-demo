package food.system.service.impl;

import food.system.dto.CategoryDto;
import food.system.entity.Category;
import food.system.repository.CategoryRepository;
import food.system.service.main.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static food.system.helper.ResponseEntityHelper.*;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
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
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> updateCategory(CategoryDto categoryDto) {
        try {
            if(categoryRepository.existsByName(categoryDto.getName()))
                return INTERNAL_ERROR();
            Category category = new Category(categoryDto.getId(), categoryDto.getName());
            categoryRepository.save(category);
            return OK_MESSAGE();
        }catch (Exception e) {
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> findCategoryById(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty())
            return NOT_FOUND();
        CategoryDto categoryDto = new CategoryDto(categoryOptional.get().getId(), categoryOptional.get().getName());
        return ResponseEntity.ok(categoryDto);
    }

    @Override
    public ResponseEntity<?> deleteCategoryById(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return OK_MESSAGE();
        }catch (Exception e) {
            return INTERNAL_ERROR();
        }
    }

    @Override
    public ResponseEntity<?> search(String name) {
        List<CategoryDto> categoryDtoList =
                categoryRepository.findAllByNameContainsIgnoreCase(name).stream()
                        .map(c -> new CategoryDto(c.getId(), c.getName())).toList();
        return ResponseEntity.ok(categoryDtoList);
    }


    @Override
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDto> categoryDtoList =
                categoryRepository.findAll().stream()
                        .map(c -> new CategoryDto(c.getId(), c.getName())).toList();
        return ResponseEntity.ok(categoryDtoList);
    }


}
