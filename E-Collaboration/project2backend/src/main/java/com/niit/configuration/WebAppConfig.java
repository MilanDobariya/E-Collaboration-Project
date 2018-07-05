package com.niit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc  //<mvc:annotation-driven> tag in dispatcher-servlet.xml
@ComponentScan(basePackages="com.niit")
public class WebAppConfig extends WebMvcConfigurerAdapter {
	public WebAppConfig() {
		System.out.println("webAppConfig class object created");
	}
	@Bean(name="multipartResolver")
	//public CommonsMultipartResolver getCommonsMultipartResolver(){
		//return new CommonsMultipartResolver();
	  public CommonsMultipartResolver getCommonsMultipartResolver() {
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    	multipartResolver.setMaxUploadSize(20971520); // 20MB
    	multipartResolver.setMaxInMemorySize(1048576);	// 1MB
    	return multipartResolver;
    
}
}
