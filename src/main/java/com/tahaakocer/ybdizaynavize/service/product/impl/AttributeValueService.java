package com.tahaakocer.ybdizaynavize.service.product.impl;


import com.tahaakocer.ybdizaynavize.dto.product.AttributeDto;
import com.tahaakocer.ybdizaynavize.dto.product.AttributeValueDto;
import com.tahaakocer.ybdizaynavize.dto.product.response.AttributeValueResponse;
import com.tahaakocer.ybdizaynavize.exception.EntityNotFoundException;
import com.tahaakocer.ybdizaynavize.mapper.product.AttributeValueMapper;
import com.tahaakocer.ybdizaynavize.model.product.AttributeValue;
import com.tahaakocer.ybdizaynavize.repository.product.AttributeValueRepository;
import com.tahaakocer.ybdizaynavize.service.product.IAttributeValueService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class AttributeValueService implements IAttributeValueService {
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeService attributeService;
    private final AttributeValueMapper attributeValueMapper;

    public AttributeValueService(AttributeValueRepository attributeValueRepository,
                                 AttributeService attributeService,
                                 AttributeValueMapper attributeValueMapper) {
        this.attributeValueRepository = attributeValueRepository;
        this.attributeService = attributeService;
        this.attributeValueMapper = attributeValueMapper;
    }

    @Override
    public AttributeValueDto create(Long attributeId, String value) {
        AttributeDto attributeDto = this.attributeService.getById(attributeId);
        if(attributeDto != null){
            AttributeValueDto attributeValueDto = AttributeValueDto.builder()
                    .attribute(attributeDto)
                    .attributeValue(value)
                    .build();

            AttributeValue saved = this.attributeValueRepository.save(
                    this.attributeValueMapper.dtoToEntity(attributeValueDto));
            log.info("Attribute value created: {}", attributeValueDto);
            return this.attributeValueMapper.entityToDto(saved);
        }
        return null;
    }

    @Override
    public AttributeValueDto getById(Long id) {

        AttributeValue attributeValue = this.attributeValueRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Attribute value not found with id: " + id)
        );
        log.info("Attribute value found: {}", attributeValue);
        AttributeValueDto attributeValueDto = this.attributeValueMapper.entityToDto(attributeValue);
        return attributeValueDto;
    }

    @Override
    public String delete(Long id) {
        AttributeValue attributeValue = this.attributeValueRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Attribute value not found with id: " + id));

        this.attributeValueRepository.delete(attributeValue);
        log.info("Attribute value deleted: {}", attributeValue);
        return "Attribute value deleted by id: " + id;
    }

    @Override
    public List<AttributeValueDto> getAll() {
        List<AttributeValue> attributeValues = this.attributeValueRepository.findAll();
        log.info("Attribute values found: {}", attributeValues);
        return this.attributeValueMapper.entityListToDtoList(attributeValues);
    }
}
