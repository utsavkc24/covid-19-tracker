package com.selfproject.coronavirus.covid19tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
/**
 * The @EnableScheduling annotation is used to enable the scheduler for your
 * application. This annotation should be added into the main Spring Boot
 * application class file.
 */
@EnableScheduling
public class Covid19TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackerApplication.class, args);
	}

}
