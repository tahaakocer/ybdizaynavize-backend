package com.tahaakocer.ybdizaynavize.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariantUpdateRequest {

    private List<Long> attributeValueIds;
    private Double price;
    private Double discountedPrice;
    private Integer stock;
}
