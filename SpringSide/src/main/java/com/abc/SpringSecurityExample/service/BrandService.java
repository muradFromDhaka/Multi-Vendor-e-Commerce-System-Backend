package com.abc.SpringSecurityExample.service;

import com.abc.SpringSecurityExample.DTOs.projectDtos.BrandRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.BrandResponseDto;
import com.abc.SpringSecurityExample.entity.Brand;
import com.abc.SpringSecurityExample.mapper.BrandMapper;
import com.abc.SpringSecurityExample.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final FileStorageService fileStorageService;

    public BrandResponseDto create(BrandRequestDto dto, MultipartFile logo) throws IOException {
        Brand brand = BrandMapper.toEntity(dto);

        // Handle logo upload
        if (logo != null && !logo.isEmpty()) {
            String fileName = fileStorageService.saveFile(logo);
            brand.setLogoUrl(fileName);
        }

        Brand saved = brandRepository.save(brand);
        return BrandMapper.toResponseDto(saved);
    }

    @Transactional
    public BrandResponseDto update(Long id, BrandRequestDto dto, MultipartFile logo) throws IOException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());

        if (logo != null && !logo.isEmpty()) {
            if (brand.getLogoUrl() != null) {
                fileStorageService.deleteFile(brand.getLogoUrl());
            }
            String fileName = fileStorageService.saveFile(logo);
            brand.setLogoUrl(fileName);
        }

        Brand updated = brandRepository.save(brand);
        return BrandMapper.toResponseDto(updated);
    }

    @Transactional
    public void delete(Long id) throws IOException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        // Delete logo file if exists
        if (brand.getLogoUrl() != null) {
            fileStorageService.deleteFile(brand.getLogoUrl());
        }

        brandRepository.delete(brand);
    }

    public BrandResponseDto getById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        return BrandMapper.toResponseDto(brand);
    }

    public Page<BrandResponseDto> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return brandRepository.findAll(pageable)
                .map(BrandMapper::toResponseDto);
    }
}
