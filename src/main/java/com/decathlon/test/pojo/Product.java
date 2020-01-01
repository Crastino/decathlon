package com.decathlon.test.pojo;

import java.util.List;

public class Product {

	private long id;
	private String code;
	private String name;
	private String description;
	private String image;
	private double price;
	private List<Feature> featureList;
	
	public Product() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<Feature> getFeatureList() {
		return featureList;
	}
	public void setFeatureList(List<Feature> featureList) {
		this.featureList = featureList;
	}
	
}
