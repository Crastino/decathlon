package com.decathlon.test.exchange;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;

import com.decathlon.test.exception.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BancaItaliaClient {

		private static final Logger LOG = LoggerFactory.getLogger(BancaItaliaClient.class);
		
		public final static SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
		
		@Value("${external.url}")
		private String externalWsURL;
		
		@Value("${country.name}")
		private String country;
		
		public double callExternalService(double value) throws ServiceException, JsonParseException, JsonMappingException, IOException {
			
			String extUrl;
			double dollarValue = 0.0d;
			// calcolo la data di ieri perché la banca d'Italia non ha ancora pubblicato i dati odierni
			String yesterday = sdfo.format(yesterday());
			
			extUrl = externalWsURL.replace("yesterday", yesterday);
			
			RestTemplate restTemplate = new RestTemplate();

		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		    HttpEntity<String> entity = new HttpEntity<String>(headers);

		    // eseguo chiamata al WS esterno
		    ResponseEntity<String> response = restTemplate.exchange(extUrl, HttpMethod.GET, entity, String.class);


		    if (response != null && response.getStatusCode() == HttpStatus.OK) {
		    	
		    	LOG.info("ok");
		    	String responseBody = response.getBody();
		    	
		    	ObjectMapper objectMapper = new ObjectMapper();
		    	
		    	Rates rates = objectMapper.readValue(responseBody, new TypeReference<Rates>() {});
		    	List<Rate> rateList = rates.getRates();
		    	for (Rate rate : rateList) {
					if(country.equals(rate.getCountry())) {
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
		
		private Date yesterday() {
			 final Calendar cal = Calendar.getInstance();
			    cal.add(Calendar.DATE, -1);
			    return cal.getTime();
		}
}
