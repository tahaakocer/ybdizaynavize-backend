package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.response.AttributeResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.AttributeMapper;
import com.tahaakocer.ybdizaynavize.service.product.IAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attributes")
public class AttributeController {
    private final IAttributeService attributeService;
    private final AttributeMapper attributeMapper;

    public AttributeController(IAttributeService attributeService, AttributeMapper attributeMapper) {
        this.attributeService = attributeService;
        this.attributeMapper = attributeMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<AttributeResponse> create(@RequestParam String name) {
        return ResponseEntity.ok().body(this.attributeMapper.dtoToResponse(
                this.attributeService.create(name)));
    }
    @GetMapping("/get-by-id")
    public ResponseEntity<AttributeResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok().body(this.attributeMapper.dtoToResponse(this.attributeService.getById(id)));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
         return ResponseEntity.ok().body(this.attributeService.delete(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<AttributeResponse>> getAll() {
        return ResponseEntity.ok().body(this.attributeMapper.dtoListToResponseList(this.attributeService.getAll()));
    }


}
