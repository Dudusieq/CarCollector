package com.example.CarCollector.map;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMap {
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "id", source = "id")
    CarDTO toCarDTO(Car Car);
    Car toCar(CarDTO CarDTO);
}


