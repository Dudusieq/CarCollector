package com.example.CarCollector.controller;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.service.CarService;
import com.example.CarCollector.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final ICarService carService;

    public CarController(ICarService carService) {
        this.carService = carService;
    }


    @PostMapping
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return carService.createCar(carDTO);
    }

    @GetMapping
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public CarDTO getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        return carService.updateCar(id, carDTO);
    }

}
