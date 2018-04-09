package com.ikeeps.pippy.authservice;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.ikeeps.pippy.authservice.domain.User;
import com.ikeeps.pippy.authservice.service.UserService;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableConfigurationProperties(Pwds.class)
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServiceApplication {
	private Logger logger = LoggerFactory.getLogger(AuthServiceApplication.class);
	
	@Autowired
	private UserService usrService;
	
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	
//	@Bean
	public CommandLineRunner initUsers() {
		return (args) -> {
			// @formatter:off
				Stream
					.of("ikee", "user", "usr", "admin", "adminstrator")
					.map(name -> {
						User usr = new User();
						usr.setUsername(name);
						usr.setPassword("123456");
						return usr;
					})
					.forEach(usr -> {
						try {
							usrService.create(usr);
						} catch (IllegalArgumentException e) {
							logger.error(usr.getUsername() + "exits");
						}
					});
			// @formatter:on

			
		};
	}
}
