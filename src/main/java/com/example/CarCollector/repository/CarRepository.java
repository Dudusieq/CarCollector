package com.example.CarCollector.repository;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwnerNameContainingIgnoreCase(String ownerName);
    List<Car> findByMarkaContainingIgnoreCase(String marka);
    List<Car> findByEngineContainingIgnoreCase(String engine);
    List<Car> findByPower(Integer power);
    List<Car> findByModelContainingIgnoreCase(String model);
    List<Car> findBySpeedup(Double speedup);
}

