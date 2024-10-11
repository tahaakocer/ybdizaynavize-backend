package com.tahaakocer.ybdizaynavize.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ProductVariantDto {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private CategoryDto category;
    private Long brandId;
    private BrandDto brand;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
