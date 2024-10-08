package com.tahaakocer.ybdizaynavize.repository.product;

import com.tahaakocer.ybdizaynavize.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
