package com.dealchecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dealchecker.model.DeleteMeClass;

@Configuration
public class DeleteMeConfigBeans {

	@Bean
	public DeleteMeClass deleteMeClassBean() {
		return new DeleteMeClass();
	}
}
