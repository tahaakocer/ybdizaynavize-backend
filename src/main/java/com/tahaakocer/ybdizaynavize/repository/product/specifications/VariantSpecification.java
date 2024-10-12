package com.tahaakocer.ybdizaynavize.repository.product.specifications;

import com.tahaakocer.ybdizaynavize.model.product.Variant;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class VariantSpecification {
    public static Specification<Variant> hasAttributeValue(List<Integer> attributeValues) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = root.join("attributeValues").get("id").in(attributeValues);
            return criteriaBuilder.and(predicate);
        };
    }
}
