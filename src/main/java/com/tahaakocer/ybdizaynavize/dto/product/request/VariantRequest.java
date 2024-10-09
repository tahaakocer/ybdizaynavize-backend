package com.tahaakocer.ybdizaynavize.dto.product.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.tahaakocer.ybdizaynavize.model.product.AttributeValue;
import com.tahaakocer.ybdizaynavize.model.product.Image;
import com.tahaakocer.ybdizaynavize.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class VariantRequest {
    private Product product;
    private String sku;
//    TODO attribute value ids
    private Double price;
    private Double discountedPrice;
    private Integer stock;
    private List<Image> images;
//    TODO muiltipartphotofile
}
