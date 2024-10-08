package com.tahaakocer.ybdizaynavize.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "variants")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

//    TODO bunu otomatik oluşturmak lazım
    private String sku;

    @ManyToMany
    @JoinTable(
            name = "variant_attributevalue",
            joinColumns = @JoinColumn(name = "attributevalue_id"),
            inverseJoinColumns = @JoinColumn(name = "variant_id")
    )
    private List<AttributeValue> attributeValues = new ArrayList<>();

    @NotNull(message = "price is required")
    private Double price;

    private Double discountedPrice;

    @NotNull(message = "stock is required")
    private Integer stock;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

}
