package com.example.securityApiJWT.Service.ProductService;

import com.example.securityApiJWT.DTO.CategoryDTO;
import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.DTO.ProductDTO;
import com.example.securityApiJWT.Exception.ResourceNotFoundException;
import com.example.securityApiJWT.Helper.Helper;
import com.example.securityApiJWT.Model.Category;
import com.example.securityApiJWT.Model.Product;
import com.example.securityApiJWT.Repository.CategoryRepository;
import com.example.securityApiJWT.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    public final ProductRepository productRepository;
    public final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        var product = Product.builder()
                .title(productDTO.getTitle())
                .description(productDTO.getDescription())
                .price(productDTO.getDiscountedPrice() == 0 ? productDTO.getPrice() : productDTO.getPrice() * (productDTO.getDiscountedPrice()/100))
                .quantity(productDTO.getQuantity())
                .productImageName(productDTO.getProductImageName())
                .build();
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given ID!"));
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getDiscountedPrice() == 0 ? productDTO.getPrice() : productDTO.getPrice() * (productDTO.getDiscountedPrice()/100));
        product.setQuantity(productDTO.getQuantity());
        product.setProductImageName(productDTO.getProductImageName());
        Product productUpdated = productRepository.save(product);
        return mapper.map(productUpdated, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given ID!"));
        productRepository.delete(product);
    }

    @Override
    public ProductDTO getProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given ID!"));
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public PageableResponse<ProductDTO> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDTO.class);
    }

    @Override
    public PageableResponse<ProductDTO> getAllEnabledProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findProductEnabled(pageable);
        return Helper.getPageableResponse(page, ProductDTO.class);
    }

    @Override
    public PageableResponse<ProductDTO> searchProductByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitleContaining(title, pageable);
        return Helper.getPageableResponse(page, ProductDTO.class);
    }

    @Override
    public ProductDTO addCategoryToProduct(ProductDTO productDTO, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product product = mapper.map(productDTO, Product.class);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateCategoryToProduct(Integer productId, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given ID!"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public PageableResponse<ProductDTO> getAllProductByCategory(Integer categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given ID!"));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByCategory(category, pageable);
        return Helper.getPageableResponse(page, ProductDTO.class);
    }
}
