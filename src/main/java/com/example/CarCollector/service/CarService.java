package com.example.CarCollector.service;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.map.CarMap;
import com.example.CarCollector.model.Car;
import com.example.CarCollector.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {

    private final CarRepository carRepository;

    private final CarMap carMap;

    public CarService(CarRepository carRepository, CarMap carMap) {
        this.carRepository = carRepository;
        this.carMap = carMap;
    }
    //dodanie pojazdu
    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Car car = carMap.toCar(carDTO);
        Car savedCar = carRepository.save(car);
        return carMap.toCarDTO(savedCar);
    }
    //pobranie wszystkich pojazdow
    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMap::toCarDTO)
                .toList();
    }
    //pobieranie jednego pojazdu za pomoca id
    @Override
    public CarDTO getCarById(Long id) {
        return carRepository.findById(id)
                .map(carMap::toCarDTO)
                .orElseThrow(() -> new RuntimeException("Samochód nie znaleziony"));
    }
    //wyszukanie marki pojazdu
    public List<CarDTO> searchCarsByBrandcar(String brandcar) {
        return carRepository.findByBrandcarContainingIgnoreCase(brandcar)
                .stream()
                .map(carMap::toCarDTO)
                .toList();
    }
    //wyszkuanie wlasciciela pojazdu
    public List<CarDTO> searchCarsByOwner(String ownerName) {
        return carRepository.findByOwnerNameContainingIgnoreCase(ownerName)
                .stream()
                .map(carMap::toCarDTO)
                .toList();
    }

    //usuwanie pojazdu
    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
    //aktualizacja danych pojazdu
    @Override
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        return carRepository.findById(id).map(car -> {

            car.setId(carDTO.getId());
            car.setBrandcar(carDTO.getBrandcar());
            car.setModel(carDTO.getModel());
            car.setPower(carDTO.getPower());
            car.setSpeedUp(carDTO.getSpeedUp());
            car.setEngine(carDTO.getEngine());

            car.setOwner(carRepository.findById(carDTO.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("Właściciel nie został znaleziony")).getOwner());

            car.setBrand(carRepository.findById(carDTO.getBrand().getId())
                    .orElseThrow(() -> new RuntimeException("Marka nie została znaleziona")).getBrand());



            Car updatedCar = carRepository.save(car);
            return carMap.toCarDTO(updatedCar);
        }).orElseThrow(() -> new RuntimeException("Samochód nie został znaleziony"));
    }

    public List<CarDTO> searchCarsByEngine(String engine) {
        return carRepository.findByEngineContainingIgnoreCase(engine)
                .stream()
                .map(carMap::toCarDTO)
                .toList();
    }

    public List<CarDTO> searchCarsByPower(String power) {
        return carRepository.findByPower(Integer.valueOf(power))
                .stream()
                .map(carMap::toCarDTO)
                .toList();
    }

    public List<CarDTO> searchCarsByModel(String model) {
        List<Car> cars = carRepository.findByModelContainingIgnoreCase(model);
        return cars.stream()
                .map(carMap::toCarDTO)
                .toList();
    }

    public List<CarDTO> searchCarsBySpeedUp(Double speedup) {
        List<Car> cars = carRepository.findBySpeedup(speedup);
        return cars.stream()
                .map(carMap::toCarDTO)
                .toList();
    }




}
