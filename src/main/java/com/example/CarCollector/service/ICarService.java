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

    List<CarDTO> searchCarsByMarka(String marka);

    List<CarDTO> searchCarsByOwner(String ownerName);

    List<CarDTO> searchCarsBySilnik(String silnik);

    List<CarDTO> searchCarsByMoc(String moc);
}
