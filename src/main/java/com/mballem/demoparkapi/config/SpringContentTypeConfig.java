package com.mballem.demoparkapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
	@Configuration
	public class SpringContentTypeConfig implements WebMvcConfigurer {
	    @Override
	    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	        configurer.defaultContentType(MediaType.APPLICATION_JSON);
	    }
	}
		