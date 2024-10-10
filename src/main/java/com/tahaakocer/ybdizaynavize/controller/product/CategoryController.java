package com.tahaakocer.ybdizaynavize.controller.product;

import com.tahaakocer.ybdizaynavize.dto.product.CategoryDto;
import com.tahaakocer.ybdizaynavize.dto.product.response.CategoryResponse;
import com.tahaakocer.ybdizaynavize.mapper.product.CategoryMapper;
import com.tahaakocer.ybdizaynavize.service.product.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(ICategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> create(@RequestParam String name, @RequestParam String description) {
        CategoryDto categoryDto = this.categoryService.create(name, description);
        return ResponseEntity.ok(this.categoryMapper.dtoToResponse(categoryDto));
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(this.categoryMapper.dtoListToResponseList(this.categoryService.getAll()));
    }
    @GetMapping("/get-by-id")
    public ResponseEntity<CategoryResponse> getById(@RequestParam Long id) {
        return ResponseEntity.ok(this.categoryMapper.dtoToResponse(this.categoryService.getById(id)));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return ResponseEntity.ok(this.categoryService.delete(id));
    }
}
