package com.example.CarCollector.map;

import com.example.CarCollector.dto.OwnerDTO;
import com.example.CarCollector.model.Owner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    OwnerDTO toOwnerDTO(Owner owner);
    Owner toOwner(OwnerDTO ownerDTO);
}
