package com.example.CarCollector.map;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandMapper.class, OwnerMapper.class})
public interface CarMap {
    CarDTO toCarDTO(Car Car);
    Car toCar(CarDTO CarDTO);
}


