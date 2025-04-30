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
    public List<CarDTO> searchCarsByMarka(String marka) {
        return carRepository.findByMarkaContainingIgnoreCase(marka)
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
            car.setMarka(carDTO.getMarka());
            car.setModel(carDTO.getModel());
            car.setMoc(carDTO.getMoc());
            car.setPrzyspieszenie(carDTO.getPrzyspieszenie());
            car.setSilnik(carDTO.getSilnik());

            car.setOwner(carRepository.findById(carDTO.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("Właściciel nie został znaleziony")).getOwner());

            car.setBrand(carRepository.findById(carDTO.getBrand().getId())
                    .orElseThrow(() -> new RuntimeException("Marka nie została znaleziona")).getBrand());



            Car updatedCar = carRepository.save(car);
            return carMap.toCarDTO(updatedCar);
        }).orElseThrow(() -> new RuntimeException("Samochód nie został znaleziony"));
    }

    public List<Car> findCarsByOwnerName(String name){
        return carRepository.findByOwnerNameContainingIgnoreCase(name);
    }
}
