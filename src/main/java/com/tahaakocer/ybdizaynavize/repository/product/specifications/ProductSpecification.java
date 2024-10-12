package com.tahaakocer.ybdizaynavize.repository.product.specifications;

import com.tahaakocer.ybdizaynavize.model.product.AttributeValue;
import com.tahaakocer.ybdizaynavize.model.product.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
    public static Specification<Product> hasAttributeValue(List<Integer> attributeValues) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, AttributeValue> attributeValueJoin = root.join("variants").join("attributeValues");
            Predicate predicate = attributeValueJoin.get("id").in(attributeValues);
            return criteriaBuilder.and(predicate);
        };
    }
}
