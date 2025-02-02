package com.ld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.ld")
@EntityScan(basePackages = "package com.ld.infrastructure.db.entity")
@EnableJpaRepositories(basePackages = "com.ld.infrastructure.db.repository")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

