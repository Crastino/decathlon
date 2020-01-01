package com.decathlon.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.decathlon.test.pojo.Product;

@Service
public class PracticeService {
	
private static final Logger LOG = LoggerFactory.getLogger(PracticeService.class);
	
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
		
	public List<Product> create(List<Product> product, HttpSession session) {
		List<Product> productList = (List<Product>) session.getAttribute("decathlon");
		
		if(CollectionUtils.isEmpty(productList)) {
			productList = new ArrayList<>();
		}
		
		productList.addAll(product);
		session.setAttribute("decathlon", productList);
		
		return productList;
	}

	public Product update(long id, Product product, HttpSession session) {
		List<Product> productList = (List<Product>) session.getAttribute("decathlon");
		if(!CollectionUtils.isEmpty(productList)) {
			for (Product p : productList) {
				if(p.getId() == id) {
					productList.remove(p);
					p = product;
					productList.add(product);
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
}
