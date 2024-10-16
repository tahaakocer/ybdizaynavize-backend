package com.tahaakocer.ybdizaynavize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class YbdizaynavizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(YbdizaynavizeApplication.class, args);
	}

}
