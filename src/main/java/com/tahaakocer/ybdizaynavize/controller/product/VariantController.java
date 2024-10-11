package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import com.tahaakocer.ybdizaynavize.dto.product.request.VariantRequest;
import com.tahaakocer.ybdizaynavize.dto.product.response.VariantResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.VariantMapper;
import com.tahaakocer.ybdizaynavize.service.product.impl.VariantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/variants")
public class VariantController {
    private final VariantService variantService;
    private final VariantMapper variantMapper;

    public VariantController(VariantService variantService, VariantMapper variantMapper) {
        this.variantService = variantService;
        this.variantMapper = variantMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<VariantResponse> create(VariantRequest variantRequest) {
        VariantDto variantDto = variantMapper.requestToDto(variantRequest);
        VariantDto saved = this.variantService.create(variantDto);
        return ResponseEntity.ok(variantMapper.dtoToResponse(saved));
    }
}
