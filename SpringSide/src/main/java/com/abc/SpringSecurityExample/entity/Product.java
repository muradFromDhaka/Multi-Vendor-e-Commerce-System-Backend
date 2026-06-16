package com.abc.SpringSecurityExample.entity;

import com.abc.SpringSecurityExample.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_vendor", columnList = "vendor_id"),
                @Index(name = "idx_product_category", columnList = "category_id"),
                @Index(name = "idx_product_brand", columnList = "brand_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="products")
public class Product extends BaseEntity{
	
	    @Column(nullable = false, length=100, name= "product_name")
	    private String name;

	    @Column(length=1000)
	    private String description;
        
	    @Column(nullable = false, precision= 10, scale=2)
	    private BigDecimal price;

        @Column(nullable = false)
        private Integer soldCount = 0;


	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "category_id")
	    private Category category;
	    
	    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Deal> deals;
	    
	    
	    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	    private List<Review> reviews;
	    
	    
	    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	    private List<CartItem> cartItem;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

	    private LocalDate releaseDate;

    @Column(unique = true, nullable = false)
    private String sku;

    private Double averageRating;
	private Integer totalReviews;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> variants;

    private BigDecimal discountPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

	    @ElementCollection
	    @CollectionTable(name="product_images", joinColumns = @JoinColumn(name="product_id"))
	    @Column(name="image_url")
	    private List<String> imageUrls;
	    
	    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	    private Set<Wishlist> wishlists;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "vendor_id")
	    private Vendor vendor;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Inventory> inventories;
	    
}
