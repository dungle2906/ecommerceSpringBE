package com.example.securityApiJWT.Service.ProductService;

import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.DTO.ProductDTO;

public interface IProductService {
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO, Integer productId);
    void deleteProduct(Integer productId);
    ProductDTO getProduct(Integer productId);
    PageableResponse<ProductDTO> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<ProductDTO> getAllEnabledProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<ProductDTO> searchProductByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir);
    ProductDTO addCategoryToProduct(ProductDTO productDTO, Integer categoryId);
    ProductDTO updateCategoryToProduct(Integer productId, Integer categoryId);
    PageableResponse<ProductDTO> getAllProductByCategory(Integer categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
}
