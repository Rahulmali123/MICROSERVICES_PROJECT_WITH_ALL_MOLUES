package com.embarkx.serive_reg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SeriveRegApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeriveRegApplication.class, args);
	}

}
