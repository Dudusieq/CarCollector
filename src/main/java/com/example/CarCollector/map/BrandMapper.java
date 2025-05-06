package com.example.CarCollector.map;

import com.example.CarCollector.dto.BrandDTO;
import com.example.CarCollector.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDTO toBrandDTO(Brand brand);
    Brand toBrand(BrandDTO brandDTO);
}
