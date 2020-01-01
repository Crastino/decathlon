package com.decathlon.test.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.decathlon.test.exception.ServiceException;
import com.decathlon.test.exchange.Rate;
import com.decathlon.test.exchange.Rates;
import com.decathlon.test.pojo.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PracticeService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PracticeService.class);
			
	public final static SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");

	@Value("${external.url}")
	private String externalWsURL;

	public double getExchange(double value) throws Exception {
		
		double dollarValue = 0.0d;
		String yesterday = sdfo.format(yesterday());
		externalWsURL += "?referenceDate=" + yesterday + "&currencyIsoCode=EUR";
		
		RestTemplate restTemplate = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	    HttpEntity<String> entity = new HttpEntity<String>(headers);

	    ResponseEntity<String> response = restTemplate.exchange(externalWsURL, HttpMethod.GET, entity, String.class);


	    if (response != null && response.getStatusCode() == HttpStatus.OK) {
	    	
	    	LOG.info("ok");
	    	String responseBody = response.getBody();
	    	
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	
	    	Rates rates = objectMapper.readValue(responseBody, new TypeReference<Rates>() {});
	    	List<Rate> rateList = rates.getRates();
	    	for (Rate rate : rateList) {
				if("STATI UNITI".equals(rate.getCountry())) {
					String dollarRateString = rate.getAvgRate();
					double dollarRate = Double.parseDouble(dollarRateString);
					double dollars = dollarRate * value;
					dollarValue = Math.round(dollars * 100.0) / 100.0;
					LOG.info("Dollar value: " + dollarValue + " $");
					return dollarValue;
				}
			}
	    	
	    	if (responseBody == null) {
	    		
		    	LOG.warn("WS Response Body is null!");
		    	throw new ServiceException("Retrieved a NULL body");	    		
	    	}
	    	
	    	return dollarValue;
	    	
	    } else {
	    	
	    	LOG.warn("WS Response not valid!");
	    	throw new ServiceException("Unable to retrieve external informations");
	    }
	}
	
	public List<Product> getAll(HttpSession session) {
		return (List<Product>) session.getAttribute("decathlon");
	}
	
	
	public Product getById(long id, HttpSession session) {
		List<Product> productList = (List<Product>) session.getAttribute("decathlon");
		if(!CollectionUtils.isEmpty(productList)) {
			for (Product product : productList) {
				if(product.getId() == id) {
					return product;
				}
			}
		}
		return null;
	}
		
	public List<Product> create(List<Product> productList, HttpSession session) {
		List<Product> sessionList = (List<Product>) session.getAttribute("decathlon");
		List<Product> listToCheck = new ArrayList<>(productList);
		
		if(CollectionUtils.isEmpty(sessionList)) {
			sessionList = new ArrayList<>();
		}
		
		for (Product product : listToCheck) {
			for (Product sessionProduct : sessionList) {
				if(sessionProduct.getId() == product.getId()) {
					productList.remove(product);
				}
			}
		}
		sessionList.addAll(productList);
		session.setAttribute("decathlon", sessionList);
		
		return sessionList;
	}

	public Product update(long id, Product product, HttpSession session) {
		List<Product> sessionList = (List<Product>) session.getAttribute("decathlon");
		if(!CollectionUtils.isEmpty(sessionList)) {
			for (Product p : sessionList) {
				if(p.getId() == id) {
					sessionList.remove(p);
					p = product;
					sessionList.add(product);
					return p;
				}
			}
		}
		
		return null;
	}
	
	public void delete(long id, HttpSession session) {
		List<Product> productList = (List<Product>) session.getAttribute("decathlon");
		if(!CollectionUtils.isEmpty(productList)) {
			for (Product product : productList) {
				if(product.getId() == id) {
					productList.remove(product);
					 break;
				}
			}
		}
	}
	
	public void deleteAll(HttpSession session) {
		session.setAttribute("decathlon", new ArrayList<>());
	}
	
	private Date yesterday() {
		 final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -1);
		    return cal.getTime();
	}
}
