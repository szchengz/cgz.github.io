package com.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

/**
 * Created by cgz on 2019-03-31 0:27
 * 描述：
 */
@SpringBootApplication
public class SpringBootApp implements CommandLineRunner{

    @Autowired
    FsShell fsShell ;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
