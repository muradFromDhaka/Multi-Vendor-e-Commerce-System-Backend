package com.abc.SpringSecurityExample.Controller;

import com.abc.SpringSecurityExample.DTOs.projectDtos.ProductRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.ProductResponseDto;
import com.abc.SpringSecurityExample.entity.Product;
import com.abc.SpringSecurityExample.entity.Vendor;
import com.abc.SpringSecurityExample.enums.ProductStatus;
import com.abc.SpringSecurityExample.repository.VendorRepository;
import com.abc.SpringSecurityExample.service.ProductService;
import com.abc.SpringSecurityExample.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final VendorRepository vendorRepository;

    // ---------------- CREATE PRODUCT ----------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestPart("product") ProductRequestDto dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        ProductResponseDto created = productService.createProduct(dto, images);
        return ResponseEntity.ok(created);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductRequestDto dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        return productService.updateProduct(id, dto, images);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            Pageable pageable
    ) {
        Page<ProductResponseDto> products = productService.getAllProducts( pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/most-popular")
    public ResponseEntity<Page<ProductResponseDto>> getMostPopular(Pageable pageable) {
        return ResponseEntity.ok(productService.getMostPopularProducts(pageable));
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<ProductResponseDto>> getLatest(Pageable pageable) {
        return ResponseEntity.ok(productService.getLatestProducts(pageable));
    }

    @GetMapping("/discounted")
    public ResponseEntity<Page<ProductResponseDto>> getDiscounted(Pageable pageable) {
        return ResponseEntity.ok(productService.getDiscountedProducts( pageable));
    }

    @GetMapping("/trending")
    public ResponseEntity<Page<ProductResponseDto>> getTrending(Pageable pageable) {
        return ResponseEntity.ok(productService.getTrendingProducts(pageable));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(@PathVariable Long categoryId, Pageable pageable)
              {
        Page<ProductResponseDto> products = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(products);
    }



    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProducts(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(
                productService.searchProducts(keyword,pageable)
        );
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByBrand(@PathVariable Long brandId,
                                                                       Pageable pageable
                                                       ) {
        return ResponseEntity.ok(
                productService.getProductsByBrandDto(brandId, pageable)
        );
    }

    @GetMapping("/vendor/{vendorId}")
    public Page<ProductResponseDto> getProductsByVendor(@PathVariable Long vendorId,
                                                        Pageable pageable
                                                        ) {
        return productService.getProductsByVendorId(vendorId, pageable);
    }

    @GetMapping("/my/product")
    public Page<ProductResponseDto> getMyProducts(
            Authentication authentication,
            Pageable pageable
            ) {
        String username = authentication.getName(); // get logged-in username
        Vendor vendor = vendorRepository.findByUserUserName(username)
                .orElseThrow(() -> new RuntimeException("Vendor not found for this user"));
        return productService.getProductsByVendorId(vendor.getId(), pageable);
    }



}
