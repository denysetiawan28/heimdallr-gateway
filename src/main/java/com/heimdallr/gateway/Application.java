package com.heimdallr.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.heimdallr.gateway.Application;
import com.heimdallr.gateway.filters.ErrorFilter;
import com.heimdallr.gateway.filters.PostFilter;
import com.heimdallr.gateway.filters.PreFilter;
import com.heimdallr.gateway.filters.RouteFilter;

@EnableZuulProxy
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
	
	public static void main(String[]args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
