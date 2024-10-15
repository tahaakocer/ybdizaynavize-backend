package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import com.tahaakocer.ybdizaynavize.dto.product.request.VariantRequest;
import com.tahaakocer.ybdizaynavize.dto.product.request.VariantUpdateRequest;
import com.tahaakocer.ybdizaynavize.dto.product.response.VariantByIdResponse;
import com.tahaakocer.ybdizaynavize.dto.product.response.VariantResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.VariantMapper;
import com.tahaakocer.ybdizaynavize.service.product.IVariantService;
import com.tahaakocer.ybdizaynavize.service.product.impl.VariantService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<VariantResponse>> getAll() {
        return ResponseEntity.ok(
                this.variantService.getAll().stream().map(variantMapper::dtoToResponse).toList()
        );
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<VariantResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok(variantMapper.dtoToResponse(this.variantService.getById(id)));
    }

    @GetMapping("/get-by-product-id")
    public ResponseEntity<List<VariantByIdResponse>> getByProductId(@RequestParam Long id) {
        return ResponseEntity.ok(
                this.variantService.getByProductId(id).stream().map(
                        variantMapper::dtoToVariantByIdResponse).toList()
        );
    }

    @GetMapping("/filter-variants-by-attribute-values")
    public ResponseEntity<List<VariantByIdResponse>> filterProductsByAttributeValues(
            @RequestParam List<Integer> attributeValues) {
        return ResponseEntity.ok(
                this.variantService.filterVariantsByAttributeValues(attributeValues).stream().map(
                        variantMapper::dtoToVariantByIdResponse).toList()
        );
    }

    @GetMapping("/get-all-paginated")
    public ResponseEntity<Page<VariantResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(
                this.variantService.getAll(page, size, sortBy, sortDirection)
                        .map(variantMapper::dtoToResponse)
        );
    }

    @GetMapping("/filter-variants-by-attribute-values-paginated")
    public ResponseEntity<Page<VariantResponse>> filterVariantsByAttributeValues(
            @RequestParam List<Integer> attributeValues, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.ok(
                this.variantService.filterVariantsByAttributeValues(attributeValues, page, size, sortBy, sortDirection)
                        .map(variantMapper::dtoToResponse)
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return ResponseEntity.ok(this.variantService.delete(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<VariantResponse> update(@PathVariable Long id, VariantUpdateRequest variantUpdateRequest) {
        VariantDto variantDto = variantMapper.updateRequestToDto(variantUpdateRequest);
        return ResponseEntity.ok(variantMapper.dtoToResponse(this.variantService.update(id, variantDto)));
    }
}
