package com.syncteam.hiresync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HiresyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiresyncApplication.class, args);
	}

}
