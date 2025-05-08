package com.projjwal.batch_job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchJobApplication {

	//37.37
	public static void main(String[] args) {
		SpringApplication.run(BatchJobApplication.class, args);
	}

}
