package com.tahaakocer.ybdizaynavize.repository.product;

import com.tahaakocer.ybdizaynavize.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
