package com.tahaakocer.ybdizaynavize.dto.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tahaakocer.ybdizaynavize.model.product.Brand;
import com.tahaakocer.ybdizaynavize.model.product.Category;
import com.tahaakocer.ybdizaynavize.model.product.Variant;
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
public class ProductResponse{

    private Long id;
    private String name;
    private String description;
    private Category category;
    private Brand brand;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
