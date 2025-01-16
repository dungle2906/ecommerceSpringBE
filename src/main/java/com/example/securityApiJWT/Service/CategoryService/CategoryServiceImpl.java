package com.example.securityApiJWT.Service.CategoryService;

import com.example.securityApiJWT.DTO.CategoryDTO;
import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.Exception.ResourceNotFoundException;
import com.example.securityApiJWT.Helper.Helper;
import com.example.securityApiJWT.Model.Category;
import com.example.securityApiJWT.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        var category = Category.builder()
                .title(categoryDTO.getTitle())
                .description(categoryDTO.getDescription())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapper.map(category, CategoryDTO.class);
    }

    @Override
    public PageableResponse<CategoryDTO> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        return Helper.getPageableResponse(page, CategoryDTO.class);
    }
}
