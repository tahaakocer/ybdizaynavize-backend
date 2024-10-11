package com.tahaakocer.ybdizaynavize.service.product.impl;

import com.tahaakocer.ybdizaynavize.dto.product.BrandDto;
import com.tahaakocer.ybdizaynavize.dto.product.CategoryDto;
import com.tahaakocer.ybdizaynavize.dto.product.ProductDto;
import com.tahaakocer.ybdizaynavize.exception.EntityNotFoundException;
import com.tahaakocer.ybdizaynavize.mapper.product.ProductMapper;
import com.tahaakocer.ybdizaynavize.model.product.Product;
import com.tahaakocer.ybdizaynavize.repository.product.ProductRepository;
import com.tahaakocer.ybdizaynavize.service.product.IProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CategoryService categoryService,
                          BrandService brandService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;

        this.brandService = brandService;
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        CategoryDto categoryDto = this.categoryService.getById(productDto.getCategoryId());
        BrandDto brandDto = this.brandService.getById(productDto.getBrandId());
        productDto.setBrand(brandDto);
        productDto.setCategory(categoryDto);
        Product product = this.productMapper.dtoToEntity(productDto);
        Product saved = this.productRepository.save(product);
        log.info("Product created: {}", saved);
        return this.productMapper.entityToDto(saved);
    }

    @Override
    public String delete(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id)
        );
        this.productRepository.delete(product);
        log.info("Product deleted: {}", product);
        return "Product deleted by id: " + id;
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id)
        );
        log.info("Product found: {}", product);
        return this.productMapper.entityToDto(product);
    }

    @Override
    public Page<ProductDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = this.productRepository.findAll(pageable);
        log.info("Products found: {}", products);
        return products.map(this.productMapper::entityToDto);
    }

    @Override
    public Page<ProductDto> getAllByCategoryId(Long categoryId, int page, int size ) {
        CategoryDto category = this.categoryService.getById(categoryId);
        if(category != null){
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> products = this.productRepository.findAllByCategoryId(categoryId, pageable);
            log.info("Products found: {}", products);
            return products.map(this.productMapper::entityToDto);
        }
       return null;
    }

    @Override
    public Page<ProductDto> getAllByBrandId(Long brandId, int page, int size) {
        BrandDto brand = this.brandService.getById(brandId);
        if(brand != null){
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> products = this.productRepository.findAllByBrandId(brandId, pageable);
            log.info("Products found: {}", products);
            return products.map(this.productMapper::entityToDto);
        }
       return null;
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        Product product = this.productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found with id: " + id)
        );
       BrandDto brandDto = this.brandService.getById(productDto.getBrandId());
       CategoryDto categoryDto = this.categoryService.getById(productDto.getCategoryId());
       productDto.setBrand(brandDto);
       productDto.setCategory(categoryDto);
       Product updated = this.productMapper.dtoToEntity(productDto);
       product.setName(updated.getName());
       product.setDescription(updated.getDescription());
       product.setBrand(updated.getBrand());
       product.setCategory(updated.getCategory());
       Product saved = this.productRepository.save(product);
       log.info("Product updated: {}", saved);
       return this.productMapper.entityToDto(saved);

    }
}
