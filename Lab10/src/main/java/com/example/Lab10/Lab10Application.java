package com.example.Lab10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.example.Lab10")
public class Lab10Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab10Application.class, args);
		System.out.println("Spring frameworkversion:"+SpringVersion.getVersion());
		System.out.println("Spring boot version:"+SpringBootVersion.getVersion());
	}

}
