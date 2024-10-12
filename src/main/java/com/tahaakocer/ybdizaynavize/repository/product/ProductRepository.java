package com.tahaakocer.ybdizaynavize.repository.product;

import com.tahaakocer.ybdizaynavize.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findAllByBrandId(Long brandId, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.variants v ORDER BY v.price ASC")
    Page<Product> findAllOrderByVariantPriceAsc(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.variants v ORDER BY v.price DESC")
    Page<Product> findAllOrderByVariantPriceDesc(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.variants v " +
            "WHERE p.id IN (SELECT p2.id FROM Product p2 JOIN p2.variants v2 JOIN v2.attributeValues av " +
            "WHERE av.id IN :attributeValues) ORDER BY v.discountedPrice ASC")
    Page<Product> findAllByAttributesOrderByVariantDiscountedPriceAsc(
            @Param("attributeValues") List<Integer> attributeValues,
            Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.variants v " +
            "WHERE p.id IN (SELECT p2.id FROM Product p2 JOIN p2.variants v2 JOIN v2.attributeValues av " +
            "WHERE av.id IN :attributeValues) ORDER BY v.discountedPrice DESC")
    Page<Product> findAllByAttributesOrderByVariantDiscountedPriceDesc(
            @Param("attributeValues") List<Integer> attributeValues,
            Pageable pageable);


}
