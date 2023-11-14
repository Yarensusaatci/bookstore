package com.postgresql.yaren_bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class YarenBookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(YarenBookstoreApplication.class, args);
	}

}
