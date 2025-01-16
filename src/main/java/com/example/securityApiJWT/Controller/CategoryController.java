package com.example.securityApiJWT.Controller;

import com.example.securityApiJWT.DTO.CategoryDTO;
import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.DTO.ProductDTO;
import com.example.securityApiJWT.DTO.ResponseMessageAPI;
import com.example.securityApiJWT.Service.CategoryService.ICategoryService;
import com.example.securityApiJWT.Service.ProductService.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    private final IProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
        //call service to   save object
        CategoryDTO categoryDto1 = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody CategoryDTO categoryDto
    ) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseMessageAPI> deleteCategory(
            @PathVariable Integer categoryId
    ) {
        categoryService.deleteCategory(categoryId);
        ResponseMessageAPI responseMessage = ResponseMessageAPI.builder()
                .message("Category deleted successfully!")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDTO>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) throws InterruptedException {
        PageableResponse<CategoryDTO> pageableResponse = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId) {
        CategoryDTO categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDTO> createProductWithCategory(
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody ProductDTO productDTO
    ) {
        ProductDTO productWithCategory = productService.addCategoryToProduct(productDTO, categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDTO> updateCategoryOfProduct(
            @PathVariable Integer categoryId,
            @PathVariable Integer productId
    ) {
        ProductDTO productDto = productService.updateCategoryToProduct(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDTO>> getAllProductByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<ProductDTO> response = productService.getAllProductByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
