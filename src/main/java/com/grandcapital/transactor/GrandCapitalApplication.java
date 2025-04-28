package com.grandcapital.transactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class GrandCapitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrandCapitalApplication.class, args);
	}
}
