package com.example.Backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.Backend.config.Dotenv.DotenvPropertySource;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.Backend.repository.jpa")
@EnableMongoRepositories(basePackages= "com.example.Backend.repository.mongo")
public class BackendApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(BackendApplication.class)
            .initializers(new DotenvPropertySource())
			.run(args);

	}

}
