package com.example.graphservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableAutoConfiguration
public class GraphServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphServiceApplication.class, args);
	}

}
