package com.decathlon.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.decathlon.test.exchange.BancaItaliaClient;
import com.decathlon.test.pojo.Product;

@Service
public class PracticeService {
	
	private static final Logger LOG = LoggerFactory.getLogger(PracticeService.class);
	private static final String SESSION_KEY = "decathlon";
		
	@Autowired
	private BancaItaliaClient client;

	public double getExchange(double value) throws Exception {
		
		LOG.info("Richiamo servizio esterno della banca d'Italia");
		return client.callExternalService(value);
	}
	
	public List<Product> getAll(HttpSession session) {
		return (List<Product>) session.getAttribute(SESSION_KEY);
	}
		
	public Product getById(long id, HttpSession session) {
		List<Product> productList = (List<Product>) session.getAttribute(SESSION_KEY);
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
		List<Product> sessionList = (List<Product>) session.getAttribute(SESSION_KEY);
		List<Product> listToCheck = new ArrayList<>(productList);
		
		// se non c'è nessun elemento, creo la lista
		if(CollectionUtils.isEmpty(sessionList)) {
			sessionList = new ArrayList<>();
		}
		
		// check che scarta record se già presenti nella lista
		for (Product product : listToCheck) {
			for (Product sessionProduct : sessionList) {
				if(sessionProduct.getId() == product.getId()) {
					productList.remove(product);
				}
			}
		}
		sessionList.addAll(productList);
		session.setAttribute(SESSION_KEY, sessionList);
		
		return sessionList;
	}

	public Product update(long id, Product product, HttpSession session) {
		List<Product> sessionList = (List<Product>) session.getAttribute(SESSION_KEY);
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
		List<Product> productList = (List<Product>) session.getAttribute(SESSION_KEY);
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
		// ricrea la lista vuota
		session.setAttribute(SESSION_KEY, new ArrayList<>());
	}

}
