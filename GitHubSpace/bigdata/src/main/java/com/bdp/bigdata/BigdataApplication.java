package com.bdp.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.bdp.controller"})
public class BigdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigdataApplication.class, args);
	}

}
