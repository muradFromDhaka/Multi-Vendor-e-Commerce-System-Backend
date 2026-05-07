package com.abc.SpringSecurityExample.Controller;

import com.abc.SpringSecurityExample.DTOs.projectDtos.VendorOrderRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.VendorOrderResponseDto;
import com.abc.SpringSecurityExample.entity.VendorOrder;
import com.abc.SpringSecurityExample.enums.VendorOrderStatus;
import com.abc.SpringSecurityExample.service.VendorOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-order")
@RequiredArgsConstructor
public class VendorOrderController {

    private final VendorOrderService vendorOrderService;

    @GetMapping
    public ResponseEntity<List<VendorOrderResponseDto>> getAll(){
        List<VendorOrderResponseDto> dto = vendorOrderService.getAll();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorOrderResponseDto> getById(@PathVariable Long id){

            VendorOrderResponseDto dto = vendorOrderService.getById(id);
            return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<VendorOrderResponseDto> create(@RequestBody VendorOrderRequestDto dto){
        VendorOrderResponseDto entity = vendorOrderService.create(dto);
        return ResponseEntity.status(201).body(entity);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<VendorOrderResponseDto> update(
            @PathVariable Long id,
            @RequestBody VendorOrderStatus status){
        VendorOrderResponseDto responseDto = vendorOrderService.updateStatus(id,status);

        return  ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        vendorOrderService.delete(id);

        return ResponseEntity.notFound().build();
    }
}
