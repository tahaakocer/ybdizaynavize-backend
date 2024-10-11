package com.tahaakocer.ybdizaynavize.service.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;

import java.util.List;

public interface IVariantService {
    VariantDto create(VariantDto variantDto);
    String delete(Long id);
    List<VariantDto> getAll();
    VariantDto getById(Long id);
}
