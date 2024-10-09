package com.tahaakocer.ybdizaynavize.service.product;

import com.tahaakocer.ybdizaynavize.dto.product.AttributeValueDto;
import com.tahaakocer.ybdizaynavize.dto.product.response.AttributeResponse;
import com.tahaakocer.ybdizaynavize.dto.product.response.AttributeValueResponse;

import java.util.List;

public interface IAttributeValueService {

    AttributeValueDto create(Long attributeId, String value);
    AttributeValueDto getById(Long id);
    String delete(Long id);
    List<AttributeValueDto> getAll();

}
