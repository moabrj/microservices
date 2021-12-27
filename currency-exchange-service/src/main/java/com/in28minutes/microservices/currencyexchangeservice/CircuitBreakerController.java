package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name="sample-api", fallbackMethod = "hardcodedResponse")
	@CircuitBreaker(name="default", fallbackMethod = "hardcodedResponse")
	public String simpleApi() {
		logger.info("Sample api request");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8888/dummy",
				String.class);
		return forEntity.getBody();
	}
	
	public String hardcodedResponse(Exception ex) {
		return "Não foi possível obter uma resposta do serviço";
	}
	
}
