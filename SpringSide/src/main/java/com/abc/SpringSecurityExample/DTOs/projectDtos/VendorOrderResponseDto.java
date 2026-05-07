package com.abc.SpringSecurityExample.DTOs.projectDtos;

import com.abc.SpringSecurityExample.enums.VendorOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class VendorOrderResponseDto {
    private Long id;
    private Long orderId;
    private Long vendorId;
    private String vendorShopName;
    private BigDecimal subtotal;
    private VendorOrderStatus status;
}
