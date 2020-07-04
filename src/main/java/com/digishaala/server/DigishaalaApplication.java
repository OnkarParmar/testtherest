package com.digishaala.server;

import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.digishaala.server.rest.PropertiesConfig;

@SpringBootApplication
public class DigishaalaApplication {
	
	@Autowired
	PropertiesConfig properties;

	public static void main(String[] args) {
		SpringApplication.run(DigishaalaApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
