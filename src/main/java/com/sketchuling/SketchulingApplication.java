package com.sketchuling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SketchulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SketchulingApplication.class, args);
	}

}
