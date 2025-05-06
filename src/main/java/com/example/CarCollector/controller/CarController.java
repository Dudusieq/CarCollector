package com.example.CarCollector.controller;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.service.ICarService;
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

    //wyszukiwanie po wlascicielu
    @GetMapping("/searchO")
    public List<CarDTO> searchCarsByOwner(@RequestParam String ownerName){
        return carService.searchCarsByOwner(ownerName);
    }

    //wyszukanie po marce samochodu
    @GetMapping("/searchB")
    public List<CarDTO> searchCarsByBrandcar(@RequestParam String brandcar) {
        return carService.searchCarsByBrandcar(brandcar);
    }

    // Wyszukiwanie po typie silnika
    @GetMapping("/searchE")
    public List<CarDTO> searchCarsByEngine(@RequestParam String engine) {
        return carService.searchCarsByEngine(engine);
    }

    // Wyszukiwanie po mocy samochodu
    @GetMapping("/searchP")
    public List<CarDTO> searchCarsByPower(@RequestParam String power) {
        return carService.searchCarsByPower(power);
    }

    // Wyszukiwanie po modelu
    @GetMapping("/searchM")
    public List<CarDTO> searchCarsByModel(@RequestParam String model) {
        return carService.searchCarsByModel(model);
    }

    // Wyszukiwanie po przyspieszeniu
    @GetMapping("/searchS")
    public List<CarDTO> searchCarsBySpeedUp(@RequestParam Double speedup) {
        return carService.searchCarsBySpeedUp(speedup);
    }
}