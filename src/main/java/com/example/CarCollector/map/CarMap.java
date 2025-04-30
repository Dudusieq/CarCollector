package com.example.CarCollector.map;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.model.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMap {
    CarDTO toCarDTO(Car Car);
    Car toCar(CarDTO CarDTO);
}

