package com.tahaakocer.ybdizaynavize.dto.product.response;

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

public class VariantResponse {
    private Long id;
    private Product product;
    private String sku;
    private List<AttributeValue> attributeValues;
    private Double price;
    private Double discountedPrice;
    private Integer stock;
    private List<Image> images;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
