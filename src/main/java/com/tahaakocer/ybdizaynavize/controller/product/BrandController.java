package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.BrandDto;
import com.tahaakocer.ybdizaynavize.dto.product.response.BrandResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.BrandMapper;
import com.tahaakocer.ybdizaynavize.service.product.IBrandService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@Log4j2
public class BrandController {

    private final IBrandService brandService;
    private final BrandMapper brandMapper;

    public BrandController(IBrandService brandService, BrandMapper brandMapper) {
        this.brandService = brandService;
        this.brandMapper = brandMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<BrandResponse> create(@RequestParam String name,@RequestParam String description) {
        BrandDto brandDto = this.brandService.create(name, description);
        return ResponseEntity.ok(this.brandMapper.dtoToResponse(brandDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BrandResponse>> getAll() {
        return ResponseEntity.ok(this.brandMapper.dtoListToResponseList(this.brandService.getAll()));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<BrandResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok(this.brandMapper.dtoToResponse(this.brandService.getById(id)));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return ResponseEntity.ok(this.brandService.delete(id));
    }
}
