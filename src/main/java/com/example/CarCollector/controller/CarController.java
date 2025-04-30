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

    //dodanie nowego pojazdu
    @PostMapping
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        return carService.createCar(carDTO);
    }
    //pobranie wszystkich pojazdow
    @GetMapping
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }
    //pobranie jednego pojazdu
    @GetMapping("/{id}")
    public CarDTO getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }
    //usuwanie pojazdu
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }
    //aktualizaja danych pojazdu
    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        return carService.updateCar(id, carDTO);
    }

}
