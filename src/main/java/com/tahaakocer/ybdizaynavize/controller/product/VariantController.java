package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import com.tahaakocer.ybdizaynavize.dto.product.request.VariantRequest;
import com.tahaakocer.ybdizaynavize.dto.product.response.VariantResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.VariantMapper;
import com.tahaakocer.ybdizaynavize.service.product.IVariantService;
import com.tahaakocer.ybdizaynavize.service.product.impl.VariantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/variants")
public class VariantController {
    private final IVariantService variantService;
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
    @GetMapping("/get-all")
    public ResponseEntity<List<VariantResponse>> getAll(){
        return ResponseEntity.ok(
                this.variantService.getAll().stream().map(variantMapper::dtoToResponse).toList()
        );
    }
    @GetMapping("/get-by-id")
    public ResponseEntity<VariantResponse> getById(@RequestParam Long id){
        return ResponseEntity.ok(variantMapper.dtoToResponse(this.variantService.getById(id)));
    }

//    TODO HER VARİANTTA TEKRAR PRODUCT GETİRMESİNE GEREK YOK, YENİ RESPONSE OLUŞTUR
    @GetMapping("/get-by-product-id")
    public ResponseEntity<List<VariantResponse>> getByProductId(@RequestParam Long id){
        return ResponseEntity.ok(this.variantService.getByProductId(id).stream().map(variantMapper::dtoToResponse).toList());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id){
        return ResponseEntity.ok(this.variantService.delete(id));
    }
}
