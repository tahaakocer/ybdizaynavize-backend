package com.tahaakocer.ybdizaynavize.service.product.impl;

import com.tahaakocer.ybdizaynavize.dto.product.CategoryDto;
import com.tahaakocer.ybdizaynavize.exception.EntityNotFoundException;
import com.tahaakocer.ybdizaynavize.mapper.product.CategoryMapper;
import com.tahaakocer.ybdizaynavize.model.product.Category;
import com.tahaakocer.ybdizaynavize.repository.product.CategoryRepository;
import com.tahaakocer.ybdizaynavize.service.product.ICategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto create(String name, String description) {
        Category category = Category.builder()
                .name(name)
                .description(description)
                .build();

        Category saved = this.categoryRepository.save(category);
        log.info("Category created: {}", saved);
        return this.categoryMapper.entityToDto(saved);
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found with id: " + id)
        );
        log.info("Category found: {}", category);
        return this.categoryMapper.entityToDto(category);
    }

    @Override
    public String delete(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found with id: " + id)
        );
        this.categoryRepository.delete(category);
        log.info("Category deleted: {}", category);
        return "Category deleted by id: " + id;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = this.categoryRepository.findAll();
        log.info("Categories found: {}", categories);
        return this.categoryMapper.entityListToDtoList(categories);
    }
}
