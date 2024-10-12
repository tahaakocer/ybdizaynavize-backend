package com.tahaakocer.ybdizaynavize.service.product;


import com.tahaakocer.ybdizaynavize.dto.product.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    ProductDto create(ProductDto productDto);
    String delete(Long id);
    ProductDto getById(Long id);
    Page<ProductDto> getAll(int page, int size);
    Page<ProductDto> getAllByCategoryId(Long categoryId,int page, int size);
    Page<ProductDto> getAllByBrandId(Long brandId,int page, int size);
    Page<ProductDto> getAllByCategoryIdAndBrandId(Long categoryId, Long brandId, int page, int size);
    Page<ProductDto> filterProductsByAttributeValues(List<Integer> attributeValues, int page, int size);
    ProductDto update(Long id, ProductDto productDto);
    boolean existsById(Long id);
}
