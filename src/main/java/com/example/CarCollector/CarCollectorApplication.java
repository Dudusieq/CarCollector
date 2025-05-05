package com.example.CarCollector;

import com.example.CarCollector.dto.CarDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.desktop.SystemSleepEvent;

@SpringBootApplication
public class CarCollectorApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarCollectorApplication.class, args);

		/*//sprawdzenie
		CarDTO carDTO = new CarDTO();
		System.out.println("Marka: " + carDTO.getMarka());
		System.out.println("Model: " + carDTO.getModel());
		System.out.println("Moc: " + carDTO.getMoc());*/
		//np
		// /cars/searchO?ownerName=Anna
		// /cars/searchM?marka=BMW
		// /cars/searchE?silnik=V8
		// /cars/searchP?moc=450



	}

}
