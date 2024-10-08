package com.tahaakocer.ybdizaynavize.repository.product;

import com.tahaakocer.ybdizaynavize.model.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
