package com.sputnik.ena.catalogservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sputnik.ena.catalogservice.multitenant.TenantInterceptor;


@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	TenantInterceptor tenantInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tenantInterceptor);
	}
}
