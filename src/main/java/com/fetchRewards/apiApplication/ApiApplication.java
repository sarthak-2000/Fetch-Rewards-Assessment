package com.fetchRewards.apiApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

/*

http://localhost:8080/h2-console
Use the following credentials:

JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
 */