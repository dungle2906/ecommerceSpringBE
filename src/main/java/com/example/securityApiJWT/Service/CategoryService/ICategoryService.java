package com.example.securityApiJWT.Service.CategoryService;

import com.example.securityApiJWT.DTO.CategoryDTO;
import com.example.securityApiJWT.DTO.PageableResponse;

public interface ICategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryDTO getCategory(Integer categoryId);
    PageableResponse<CategoryDTO> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);
}
