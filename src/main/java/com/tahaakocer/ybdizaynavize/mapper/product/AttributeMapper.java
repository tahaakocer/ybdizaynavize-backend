package com.tahaakocer.ybdizaynavize.mapper.product;

import com.tahaakocer.ybdizaynavize.dto.product.AttributeDto;
import com.tahaakocer.ybdizaynavize.dto.product.response.AttributeResponse;
import com.tahaakocer.ybdizaynavize.model.product.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AttributeMapper.class);

    Attribute dtoToEntity(AttributeDto dto);
    AttributeDto entityToDto(Attribute entity);
    AttributeResponse dtoToResponse(AttributeDto attributeDto);

    List<AttributeResponse> dtoListToResponseList(List<AttributeDto> attributeDtos);

    List<AttributeDto> entityListToDtoList(List<Attribute> all);
}
