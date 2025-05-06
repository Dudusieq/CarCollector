package com.example.CarCollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
