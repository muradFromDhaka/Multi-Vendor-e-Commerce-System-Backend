package com.abc.SpringSecurityExample.mapper;

import com.abc.SpringSecurityExample.DTOs.projectDtos.VendorOrderRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.VendorOrderResponseDto;
import com.abc.SpringSecurityExample.entity.Order;
import com.abc.SpringSecurityExample.entity.Vendor;
import com.abc.SpringSecurityExample.entity.VendorOrder;
import org.springframework.stereotype.Component;

@Component
public class VendorOrderMapper {

    public VendorOrder toEntity(VendorOrderRequestDto requestDto, VendorOrder entity){
        if(requestDto == null || entity == null) return null;

        if(requestDto.getOrderId() != null){
            Order order = new Order();
            order.setId(requestDto.getOrderId());
            entity.setOrder(order);
        }

        if(requestDto.getVendorId() != null){
           Vendor vendor = new Vendor();
           vendor.setId(requestDto.getVendorId());
           entity.setVendor(vendor);
        }

        entity.setSubtotal(requestDto.getSubtotal());

        return entity;
    }

    public VendorOrderResponseDto toDto(VendorOrder entity, VendorOrderResponseDto responseDto){
        if(entity == null) return null;

        responseDto.setId(entity.getId());
        responseDto.setSubtotal(entity.getSubtotal());
        responseDto.setStatus(entity.getStatus());

        if(entity.getOrder() != null){
            responseDto.setOrderId(entity.getOrder().getId());
        }

        if(entity.getVendor() != null){
            responseDto.setVendorId(entity.getVendor().getId());
            responseDto.setVendorShopName(entity.getVendor().getShopName());
        }

        return responseDto;
    }
}
