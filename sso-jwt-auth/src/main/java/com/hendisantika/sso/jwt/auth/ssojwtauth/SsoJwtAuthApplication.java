package com.hendisantika.sso.jwt.auth.ssojwtauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SsoJwtAuthApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SsoJwtAuthApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SsoJwtAuthApplication.class, args);
	}
}
