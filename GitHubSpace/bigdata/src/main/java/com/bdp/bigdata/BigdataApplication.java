package com.bdp.bigdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
//@ComponentScan(value = {"com.bdp"})
@ComponentScan(value="com.bdp")

//@MapperScan("com.bdp.mapper") //扫描的mapper
@SpringBootApplication
public class BigdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigdataApplication.class, args);
	}

}
