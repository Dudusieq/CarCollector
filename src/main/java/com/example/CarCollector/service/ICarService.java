package com.example.CarCollector.service;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.model.Car;

import java.util.List;

public interface ICarService {
    CarDTO createCar(CarDTO carDTO);

    List<CarDTO> getAllCars();

    CarDTO getCarById(Long id);

    void deleteCar(Long id);

    CarDTO updateCar(Long id, CarDTO carDTO);

    List<CarDTO> searchCarsByBrandcar(String brandcar);

    List<CarDTO> searchCarsByOwner(String ownerName);

    List<CarDTO> searchCarsByEngine(String engine);

    List<CarDTO> searchCarsByPower(String power);

    List<CarDTO> searchCarsByModel(String model);

    List<CarDTO> searchCarsBySpeedUp(Double speedup);
}
