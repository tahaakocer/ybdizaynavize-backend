package com.tahaakocer.ybdizaynavize.mapper.product;

import com.tahaakocer.ybdizaynavize.dto.product.BrandDto;
import com.tahaakocer.ybdizaynavize.dto.product.response.BrandResponse;
import com.tahaakocer.ybdizaynavize.model.product.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto entityToDto(Brand saved);

    List<BrandDto> entityListToDtoList(List<Brand> brands);

    BrandResponse dtoToResponse(BrandDto brandDto);

    List<BrandResponse> dtoListToResponseList(List<BrandDto> all);
}
