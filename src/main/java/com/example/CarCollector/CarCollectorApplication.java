package com.example.CarCollector;

import com.example.CarCollector.dto.CarDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.desktop.SystemSleepEvent;

@SpringBootApplication
public class CarCollectorApplication {

	public static void main(String[] args) {

		SpringApplication.run(CarCollectorApplication.class, args);

		/*
		np

		 /cars/searchO?ownerName=Anna
		 /cars/searchB?brandcar=BMW
		 /cars/searchE?engine=V8
		 /cars/searchP?power=450
		 /cars/searchModel?model=Mustang
		 /cars/searchS?speedup=4.2

		*/


    }

}
