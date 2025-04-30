package com.example.CarCollector.service;

import com.example.CarCollector.dto.CarDTO;

import java.util.List;

public interface ICarService {
    CarDTO createCar(CarDTO carDTO);

    List<CarDTO> getAllCars();

    CarDTO getCarById(Long id);

    void deleteCar(Long id);

    CarDTO updateCar(Long id, CarDTO carDTO);
}
