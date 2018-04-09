package com.ikee.pippy.registryEuraka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistryEurakaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryEurakaApplication.class, args);
	}
}
