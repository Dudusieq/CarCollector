package com.example.CarCollector.service;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.map.CarMap;
import com.example.CarCollector.model.Car;
import com.example.CarCollector.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private CarRepository carRepository;
    private CarMap carMap;
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        carMap = Mockito.mock(CarMap.class);
        carService = new CarService(carRepository, carMap);
    }

    @Test
    void createCar_returnsCreatedCarDTO() {

        CarDTO carDTO = new CarDTO();
        carDTO.setBrandcar("BMW");
        carDTO.setModel("M3");
        carDTO.setPower(450);
        carDTO.setSpeedUp(3.9);
        carDTO.setEngine("V6");

        Car car = new Car();
        car.setBrandcar("BMW");
        car.setModel("M3");
        car.setPower(450);
        car.setSpeedup(3.9);
        car.setEngine("V6");


        when(carMap.toCar(carDTO)).thenReturn(car);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        when(carMap.toCarDTO(car)).thenReturn(carDTO);

        CarDTO result = carService.createCar(carDTO);

        assertNotNull(result);
        assertEquals("BMW", result.getBrandcar());
        assertEquals("M3", result.getModel());
    }
}
