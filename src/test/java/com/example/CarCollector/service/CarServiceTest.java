package com.example.CarCollector.service;

import com.example.CarCollector.dto.CarDTO;
import com.example.CarCollector.map.CarMap;
import com.example.CarCollector.model.Car;
import com.example.CarCollector.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMap carMap;

    @InjectMocks
    private CarService carService;

    private CarDTO inputDto;
    private Car entity;
    private CarDTO outputDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // obiekty do testu
        inputDto = new CarDTO();
        inputDto.setBrandcar("Toyota");
        inputDto.setModel("Corolla");
        inputDto.setPower(140);
        inputDto.setSpeedUp(8.5);
        inputDto.setEngine("I4");


        entity = new Car();
        entity.setBrandcar("Toyota");
        entity.setModel("Corolla");
        entity.setPower(140);
        entity.setSpeedup(8.5);
        entity.setEngine("I4");

        outputDto = new CarDTO();
        outputDto.setBrandcar("Toyota");
        outputDto.setModel("Corolla");
        outputDto.setPower(140);
        outputDto.setSpeedUp(8.5);
        outputDto.setEngine("I4");
    }

    @Test
    void addCar_shouldMapDtoToEntity_saveAndMapBackToDto() {
        // smapowanie DTO -> Entity
        when(carMap.toCar(inputDto)).thenReturn(entity);
        // zachowanie repository
        when(carRepository.save(entity)).thenReturn(entity);
        // mapowanie Entity -> DTO
        when(carMap.toCarDTO(entity)).thenReturn(outputDto);

        // wywołanie metody pod testem
        CarDTO result = carService.createCar(inputDto);

        // weryfikacje
        assertNotNull(result);
        assertEquals("Toyota", result.getBrandcar());
        verify(carMap).toCar(inputDto);
        verify(carRepository).save(entity);
        verify(carMap).toCarDTO(entity);
    }

    @Test
    void getAllCars_shouldReturnListOfDtos() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(entity));
        when(carMap.toCarDTO(entity)).thenReturn(outputDto);

        var list = carService.getAllCars();

        assertEquals(1, list.size());
        assertEquals("Toyota", list.get(0).getBrandcar());
        verify(carRepository).findAll();
        verify(carMap).toCarDTO(entity);
    }

    @Test
    void getCarById_found() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(carMap.toCarDTO(entity)).thenReturn(outputDto);

        CarDTO dto = carService.getCarById(1L);

        assertNotNull(dto);
        assertEquals("Toyota", dto.getBrandcar());
        verify(carRepository).findById(1L);
        verify(carMap).toCarDTO(entity);
    }

    @Test
    void getCarById_notFound_throws() {
        when(carRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> carService.getCarById(2L));
    }

}
