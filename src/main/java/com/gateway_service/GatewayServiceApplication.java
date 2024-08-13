package com.gateway_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

	@Autowired
	private AuthenticationFilter filter;

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("related-service", r -> r.path("/related/**")
						.filters(f -> f.filter(filter))
						.uri("lb://RELATED-SERVICE"))
				.route("recommendation-service", r -> r.path("/recommendation/**")
						.filters(f -> f.filter(filter))
						.uri("lb://RECOMMENDATION-SERVICE"))
				.build();
	}
}
