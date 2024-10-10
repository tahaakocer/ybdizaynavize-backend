package com.tahaakocer.ybdizaynavize.service.product.impl;

import com.tahaakocer.ybdizaynavize.dto.product.BrandDto;
import com.tahaakocer.ybdizaynavize.exception.EntityNotFoundException;
import com.tahaakocer.ybdizaynavize.mapper.product.BrandMapper;
import com.tahaakocer.ybdizaynavize.model.product.Brand;
import com.tahaakocer.ybdizaynavize.repository.product.BrandRepository;
import com.tahaakocer.ybdizaynavize.service.product.IBrandService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDto create(String name, String description) {
        Brand brand = Brand.builder()
                .name(name)
                .description(description)
                .build();

        Brand saved = this.brandRepository.save(brand);
        log.info("Brand created: {}", saved);
        return this.brandMapper.entityToDto(saved);
    }

    @Override
    public BrandDto getById(Long id) {
        Brand brand = this.brandRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Brand not found with id: " + id)
        );
        log.info("Brand found: {}", brand);
        return this.brandMapper.entityToDto(brand);
    }

    @Override
    public String delete(Long id) {
        Brand brand = this.brandRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Brand not found with id: " + id)
        );

        this.brandRepository.delete(brand);
        log.info("Brand deleted: {}", brand);
        return "Brand deleted by id: " + id;
    }

    @Override
    public List<BrandDto> getAll() {
        List<Brand> brands = this.brandRepository.findAll();
        log.info("Brands found: {}", brands);
        return this.brandMapper.entityListToDtoList(brands);
    }
}
