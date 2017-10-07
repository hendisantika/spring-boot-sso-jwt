package com.hendisantika.sso.jwt.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class SpringBootSsoJwtApplication extends SpringBootServletInitializer {
	@Value("${services.auth}")
	private String authService;

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
		registrationBean.addUrlPatterns("/protected-resource");

		return registrationBean;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootSsoJwtApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSsoJwtApplication.class, args);
	}
}
