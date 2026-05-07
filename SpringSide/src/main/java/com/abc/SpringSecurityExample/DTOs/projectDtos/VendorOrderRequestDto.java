package com.abc.SpringSecurityExample.DTOs.projectDtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class VendorOrderRequestDto {
    private Long orderId;
    private Long vendorId;
    private BigDecimal subtotal;
}
