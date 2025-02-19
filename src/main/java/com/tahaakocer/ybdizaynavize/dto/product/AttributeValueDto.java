package com.tahaakocer.ybdizaynavize.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tahaakocer.ybdizaynavize.model.product.Attribute;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AttributeValueDto {
    private Long id;
    private Long attributeId;
    private AttributeDto attribute;
    private String attributeValue;
}
