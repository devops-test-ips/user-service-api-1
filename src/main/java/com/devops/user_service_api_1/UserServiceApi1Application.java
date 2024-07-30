package com.devops.user_service_api_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApi1Application {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApi1Application.class, args);
	}

}
