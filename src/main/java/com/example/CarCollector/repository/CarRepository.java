package com.example.CarCollector.repository;

import com.example.CarCollector.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
