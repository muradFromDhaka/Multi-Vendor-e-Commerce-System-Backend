package com.abc.SpringSecurityExample.service;

import com.abc.SpringSecurityExample.DTOs.projectDtos.VendorOrderRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.VendorOrderResponseDto;
import com.abc.SpringSecurityExample.entity.VendorOrder;
import com.abc.SpringSecurityExample.enums.VendorOrderStatus;
import com.abc.SpringSecurityExample.mapper.VendorOrderMapper;
import com.abc.SpringSecurityExample.repository.VendorOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorOrderService {

    private final VendorOrderRepository vendorOrderRepository;
    private final VendorOrderMapper vendorOrderMapper;

    // 🔥 CREATE
    public VendorOrderResponseDto create(VendorOrderRequestDto dto) {

        VendorOrder entity = vendorOrderMapper.toEntity(dto, new VendorOrder());

        // Business logic (IMPORTANT)
        entity.setStatus(VendorOrderStatus.PENDING);

        VendorOrder saved = vendorOrderRepository.save(entity);

        return vendorOrderMapper.toDto(saved, new VendorOrderResponseDto());
    }

    // 🔥 GET BY ID
    public VendorOrderResponseDto getById(Long id) {

        VendorOrder entity = vendorOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VendorOrder not found: " + id));

        return vendorOrderMapper.toDto(entity, new VendorOrderResponseDto());
    }

    // 🔥 GET ALL
    public List<VendorOrderResponseDto> getAll() {

        return vendorOrderRepository.findAll()
                .stream()
                .map(vendorOrder -> vendorOrderMapper.toDto(vendorOrder,new VendorOrderResponseDto()))
                .toList();
    }

    // 🔥 UPDATE STATUS (very common in real system)
    public VendorOrderResponseDto updateStatus(Long id, VendorOrderStatus status) {

        VendorOrder entity = vendorOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VendorOrder not found: " + id));

        entity.setStatus(status);

        VendorOrder updated = vendorOrderRepository.save(entity);

        return vendorOrderMapper.toDto(updated,new VendorOrderResponseDto());
    }

    // 🔥 DELETE
    public void delete(Long id) {

        VendorOrder entity = vendorOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VendorOrder not found: " + id));

        vendorOrderRepository.delete(entity);
    }
}
