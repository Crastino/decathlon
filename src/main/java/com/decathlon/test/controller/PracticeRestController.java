package com.decathlon.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decathlon.test.pojo.Product;
import com.decathlon.test.service.PracticeService;


@Controller
@RequestMapping("/product")
@ResponseBody()
public class PracticeRestController {

	private static final Logger LOG = LoggerFactory.getLogger(PracticeRestController.class);
	
	@Autowired
	private PracticeService service;

	
	@GetMapping()
	public List<Product> getAll(HttpServletRequest request) {
		LOG.info("GET request all");
		return service.getAll(request.getSession());
	}
	
	@GetMapping("/{id}")
	public Product getById(@PathVariable("id") long id, HttpServletRequest request) {
		LOG.info("GET request id");
		return service.getById(id, request.getSession());
	}
	
	@PostMapping()
    public List<Product> create(@RequestBody List<Product> productList, HttpServletRequest request) {
		LOG.info("POST request");
        return service.create(productList, request.getSession());
    }
	
	@PutMapping("/{id}")
    public Product update(@PathVariable("id") long id, @RequestBody Product product, HttpServletRequest request) {
		LOG.info("PUT request id");
        return service.update(id, product, request.getSession());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id, HttpServletRequest request) {
    	LOG.info("DELETE request id");
        service.delete(id, request.getSession());
    }
    
    @DeleteMapping()
    public void deleteAll(HttpServletRequest request) {
    	LOG.info("DELETE request all");
        service.deleteAll(request.getSession());
    }
    
    @GetMapping("/exchange/{value}")
	public double getExchange(@PathVariable("value") double value) throws Exception {
    	LOG.info("GET request exchange");
		return service.getExchange(value);
	}

}
