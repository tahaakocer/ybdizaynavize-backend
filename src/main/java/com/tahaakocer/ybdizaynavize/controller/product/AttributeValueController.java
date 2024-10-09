package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.response.AttributeValueResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.AttributeValueMapper;
import com.tahaakocer.ybdizaynavize.service.product.IAttributeValueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attribute-values")
public class AttributeValueController {
    private final IAttributeValueService attributeValueService;
    private final AttributeValueMapper attributeValueMapper;

    public AttributeValueController(IAttributeValueService attributeValueService,
                                    AttributeValueMapper attributeValueMapper) {
        this.attributeValueService = attributeValueService;
        this.attributeValueMapper = attributeValueMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<AttributeValueResponse> create(@RequestParam Long attributeId, @RequestParam String value) {
        return ResponseEntity.ok().body(
                this.attributeValueMapper.dtoToResponse(
                        this.attributeValueService.create(attributeId, value)));
    }
    @GetMapping("/get-by-id")
    public ResponseEntity<AttributeValueResponse> getById(@RequestParam Long id) {
        AttributeValueResponse response = attributeValueMapper.dtoToResponse(attributeValueService.getById(id));
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return ResponseEntity.ok().body(attributeValueService.delete(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<AttributeValueResponse>> getAll() {
        return ResponseEntity.ok().body(attributeValueMapper.dtoListToResponseList(attributeValueService.getAll()));
    }
}
