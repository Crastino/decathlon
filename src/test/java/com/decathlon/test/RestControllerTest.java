package com.decathlon.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.decathlon.test.pojo.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

	  @LocalServerPort
	  private int port;

	  @Autowired
	  private TestRestTemplate restTemplate;
	  

	  // TODO 
	  // Test per tutti i servizi esposti
	  
	  @Test
	    public void postProductTest() {
	        String url = "http://localhost:" + port + "/product";
	        
	        List<Product> productList = new ArrayList<>();
	        Product product = new Product();
	        product.setId(1L);
	        productList.add(product);
	        
	        List<LinkedHashMap<Object,Object>> result = restTemplate.postForObject(url, productList, List.class);

	        LinkedHashMap<Object,Object> p = result.get(0);
	        
	        assertThat(Long.parseLong(String.valueOf(p.get("id")))).isEqualTo(productList.get(0).getId());
	    }


}
