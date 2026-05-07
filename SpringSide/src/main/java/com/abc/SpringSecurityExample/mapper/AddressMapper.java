package com.abc.SpringSecurityExample.mapper;

import com.abc.SpringSecurityExample.DTOs.projectDtos.AddressRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.AddressResponseDto;
import com.abc.SpringSecurityExample.entity.Address;
import com.abc.SpringSecurityExample.entity.User;
import com.abc.SpringSecurityExample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDto dto, Address entity){
        if(dto == null || entity == null) return null;
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setStreet(dto.getStreet());
        entity.setZipCode(dto.getZipCode());

        return entity;
    }

    public AddressResponseDto toDto(Address entity, AddressResponseDto dto){
        if(entity == null || dto == null) return null;

        dto.setId(entity.getId());
        if(entity.getUser() != null){
            dto.setUserName(entity.getUser().getUserName());
        }
        dto.setStreet(entity.getStreet());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        dto.setZipCode(entity.getZipCode());

        return dto;
    }
}
