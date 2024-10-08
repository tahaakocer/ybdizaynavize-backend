package com.tahaakocer.ybdizaynavize.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tahaakocer.ybdizaynavize.model.product.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AttributeValueDto {
    private Long id;
    private Attribute attribute;
    private String attributeValue;
}
