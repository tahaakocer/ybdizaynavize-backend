package com.tahaakocer.ybdizaynavize.service.product;

import com.tahaakocer.ybdizaynavize.dto.product.BrandDto;

import java.util.List;

public interface IBrandService {
    BrandDto create(String name, String description);
    BrandDto getById(Long id);
    String delete(Long id);
    List<BrandDto> getAll();
}
