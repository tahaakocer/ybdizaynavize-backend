package com.tahaakocer.ybdizaynavize.service.product.impl;

import com.tahaakocer.ybdizaynavize.dto.product.AttributeDto;
import com.tahaakocer.ybdizaynavize.exception.EntityNotFoundException;
import com.tahaakocer.ybdizaynavize.mapper.product.AttributeMapper;
import com.tahaakocer.ybdizaynavize.model.product.Attribute;
import com.tahaakocer.ybdizaynavize.repository.product.AttributeRepository;
import com.tahaakocer.ybdizaynavize.service.product.IAttributeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
public class AttributeService implements IAttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    public AttributeService(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
        this.attributeRepository = attributeRepository;
        this.attributeMapper = attributeMapper;
    }

    @Override
    public AttributeDto create(String name) {
        Attribute attribute = Attribute.builder().name(name).build();
        Attribute saved = this.attributeRepository.save(attribute);
        log.info("Attribute created: {}", saved);
        return this.attributeMapper.entityToDto(saved);
    }

    @Override
    public String delete(Long id) {
        Attribute attribute = this.attributeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Attribute not found with id: " + id));

        this.attributeRepository.delete(attribute);
        log.info("Attribute deleted: {}", attribute);
        return "Attribute deleted by id: " + id;
    }

    @Override
    public AttributeDto getById(Long id) {
        Attribute attribute = this.attributeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Attribute not found with id: " + id));
        return this.attributeMapper.entityToDto(attribute);
    }

    @Override
    public List<AttributeDto> getAll() {
        return this.attributeMapper.entityListToDtoList(this.attributeRepository.findAll());
    }
}