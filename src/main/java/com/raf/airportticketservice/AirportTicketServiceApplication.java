package com.raf.airportticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AirportTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirportTicketServiceApplication.class, args);
	}

}
