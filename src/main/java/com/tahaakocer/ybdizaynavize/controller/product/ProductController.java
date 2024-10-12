package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.ProductDto;
import com.tahaakocer.ybdizaynavize.dto.product.request.ProductRequest;
import com.tahaakocer.ybdizaynavize.dto.product.response.ProductResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.ProductMapper;
import com.tahaakocer.ybdizaynavize.service.product.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;
    private final ProductMapper productMapper;

    public ProductController(IProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> create(ProductRequest productRequest) {
        ProductDto productDto = this.productMapper.requestToDto(productRequest);
        ProductDto saved = this.productService.create(productDto);
        return ResponseEntity.ok().body(this.productMapper.dtoToResponse(saved));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(
                this.productService.getAll(page, size, sortBy, sortDirection)
                        .map(this.productMapper::dtoToResponse)
        );
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<ProductResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok(this.productMapper.dtoToResponse(this.productService.getById(id)));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return ResponseEntity.ok(this.productService.delete(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, ProductRequest productRequest) {
        ProductDto productDto = this.productMapper.requestToDto(productRequest);
        ProductDto updated = this.productService.update(id, productDto);
        return ResponseEntity.ok(this.productMapper.dtoToResponse(updated));
    }

    @GetMapping("/get-by-category-id")
    public ResponseEntity<Page<ProductResponse>> getAllByCategoryId(
            @RequestParam Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(
                this.productService.getAllByCategoryId(categoryId, page, size, sortBy, sortDirection)
                        .map(this.productMapper::dtoToResponse)
        );
    }

    @GetMapping("/get-by-brand-id")
    public ResponseEntity<Page<ProductResponse>> getAllByBrandId(
            @RequestParam Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(
                this.productService.getAllByBrandId(brandId, page, size, sortBy, sortDirection)
                        .map(this.productMapper::dtoToResponse)
        );
    }

    @GetMapping("/filter-products-by-attribute-values")
    public ResponseEntity<Page<ProductResponse>> filterProductsByAttributeValues(
            @RequestParam List<Integer> attributeValues,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(
                this.productService.filterProductsByAttributeValues(attributeValues, page, size, sortBy, sortDirection)
                        .map(this.productMapper::dtoToResponse)
        );
    }
}



