package org.beatengine.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Onlineshop {

	public static void main(String[] args) {
		SpringApplication.run(Onlineshop.class, args);
	}

}
