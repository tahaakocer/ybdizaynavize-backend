package com.tahaakocer.ybdizaynavize.service.product.impl;

import com.tahaakocer.ybdizaynavize.dto.product.AttributeValueDto;
import com.tahaakocer.ybdizaynavize.dto.product.ProductDto;
import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import com.tahaakocer.ybdizaynavize.mapper.product.ProductMapper;
import com.tahaakocer.ybdizaynavize.mapper.product.VariantMapper;
import com.tahaakocer.ybdizaynavize.model.product.Image;
import com.tahaakocer.ybdizaynavize.model.product.Variant;
import com.tahaakocer.ybdizaynavize.repository.product.VariantRepository;
import com.tahaakocer.ybdizaynavize.service.product.AwsS3Service;
import com.tahaakocer.ybdizaynavize.service.product.IAttributeValueService;
import com.tahaakocer.ybdizaynavize.service.product.IProductService;
import com.tahaakocer.ybdizaynavize.service.product.IVariantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class VariantService implements IVariantService {
    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;
    private final AwsS3Service awsS3Service;
    private final IProductService productService;
    private final ProductMapper productMapper;
    private final IAttributeValueService attributeValueService;

    public VariantService(VariantRepository variantRepository, VariantMapper variantMapper, AwsS3Service awsS3Service, IProductService productService, ProductMapper productMapper, IAttributeValueService attributeValueService) {
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
        this.awsS3Service = awsS3Service;
        this.productService = productService;
        this.productMapper = productMapper;
        this.attributeValueService = attributeValueService;
    }

    @Override
    public VariantDto create(VariantDto variantDto) {
        //Product
        ProductDto productDto = this.productService.getById(variantDto.getProductId());
        if(productDto != null){
            variantDto.setProduct(this.productMapper.dtoToVariantDto(productDto));

            log.info("variantDto: " + variantDto);
            //AttributeValues
            List<AttributeValueDto> attributeValueDtoList = new ArrayList<>();
            variantDto.getAttributeValueIds().forEach(attributeValueId -> {
                AttributeValueDto attributeValue = this.attributeValueService.getById(attributeValueId);
                attributeValueDtoList.add(attributeValue);
            });
            variantDto.setAttributeValues(attributeValueDtoList);
            log.info("attributeValueDtoList: " + attributeValueDtoList);

            //Image
            Variant variant = this.variantMapper.dtoToEntity(variantDto);
            List<String> imageUrls = new ArrayList<>();
            if(variantDto.getPhotoFiles() != null){
                imageUrls = awsS3Service.saveMultipleImages(variantDto.getPhotoFiles());
            }
            List<Image> imageEntities = imageUrls.stream()
                    .map(imageUrl -> {
                        Image image = new Image();
                        image.setImageUrl(imageUrl);
                        return image;
                    }).toList();

            variant.setImages(imageEntities);
            log.info("imageEntities: " + imageEntities);
            //Save
            Variant savedVariant = this.variantRepository.save(variant);

            log.info("savedVariant: " + savedVariant);
            return this.variantMapper.entityToDto(savedVariant);
        }
       return null;
    }
}
