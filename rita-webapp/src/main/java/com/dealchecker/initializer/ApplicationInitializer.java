package com.dealchecker.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.dealchecker.*"})
public class ApplicationInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializer.class, args);
	}

}
