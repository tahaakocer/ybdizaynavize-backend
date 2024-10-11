package com.tahaakocer.ybdizaynavize.repository.product;

import com.tahaakocer.ybdizaynavize.dto.product.VariantDto;
import com.tahaakocer.ybdizaynavize.model.product.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    List<Variant> findAllByProductId(Long productId);
}
