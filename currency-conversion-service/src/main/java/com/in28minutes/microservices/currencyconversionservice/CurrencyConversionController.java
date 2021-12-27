package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
				
		ResponseEntity<CurrencyConversion> currenyEntity =  new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariables);
		
		return new CurrencyConversion(1000L, from, to, quantity, 
				currenyEntity.getBody().getConversionMultiple(), 
				quantity.multiply(currenyEntity.getBody().getConversionMultiple()),
				currenyEntity.getBody().getEnvironment());
		
	}
	
	
	@GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversion currenyEntity = currencyExchangeProxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(1000L, from, to, quantity, 
				currenyEntity.getConversionMultiple(), 
				quantity.multiply(currenyEntity.getConversionMultiple()),
				currenyEntity.getEnvironment());
	}
}
