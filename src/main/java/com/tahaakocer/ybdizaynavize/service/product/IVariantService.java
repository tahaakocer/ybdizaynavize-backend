package com.tahaakocer.ybdizaynavize.service.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVariantService {
    VariantDto create(VariantDto variantDto);
    String delete(Long id);
    List<VariantDto> getAll();
    VariantDto getById(Long id);
    VariantDto update(Long id, VariantDto variantDto);
    List<VariantDto> getByProductId(Long id);
    List<VariantDto> filterVariantsByAttributeValues(List<Integer> attributeValues);
    Page<VariantDto> filterVariantsByAttributeValues(List<Integer> attributeValues,int page, int size, String sortBy, String sortDirection);
    Page<VariantDto> getAll(int page, int size, String sortBy, String sortDirection);

}
