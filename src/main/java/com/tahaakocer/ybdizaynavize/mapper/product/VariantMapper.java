package com.tahaakocer.ybdizaynavize.mapper.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import com.tahaakocer.ybdizaynavize.dto.product.request.VariantRequest;
import com.tahaakocer.ybdizaynavize.dto.product.response.VariantResponse;
import com.tahaakocer.ybdizaynavize.model.product.Variant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariantMapper {
     Variant dtoToEntity(VariantDto variantDto);

    VariantDto entityToDto(Variant savedVariant);

    VariantDto requestToDto(VariantRequest variantRequest);

    VariantResponse dtoToResponse(VariantDto saved);
}
