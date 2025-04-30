package com.example.CarCollector.service;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.map.CarMap;
import com.example.CarCollector.model.Car;
import com.example.CarCollector.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMap carMap;

    public CarDTO createCar(CarDTO carDTO) {
        Car car = carMap.toCar(carDTO);
        Car savedCar = carRepository.save(car);
        return carMap.toCarDTO(savedCar);
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMap::toCarDTO)
                .toList();
    }

    public CarDTO getCarById(Long id) {
        return carRepository.findById(id)
                .map(carMap::toCarDTO)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public void deleteCar(Long id) {

        carRepository.deleteById(id);
    }


    public CarDTO updateCar(Long id, CarDTO carDTO) {
        return carRepository.findById(id).map(car -> {
            car.setMarka(carDTO.getMarka());
            car.setModel(carDTO.getModel());
            car.setMoc(carDTO.getMoc());
            Car updatedCar = carRepository.save(car);
            return carMap.toCarDTO(updatedCar);
        }).orElseThrow(() -> new RuntimeException("Samochód nie został znaleziony"));
    }
}
