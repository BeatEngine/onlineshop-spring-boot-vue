package org.beatengine.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan
@SpringBootApplication()
public class Onlineshop {

	public static void main(String[] args) {
		SpringApplication.run(Onlineshop.class, args);
	}

}
