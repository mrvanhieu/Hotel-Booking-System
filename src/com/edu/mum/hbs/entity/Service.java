package com.edu.mum.hbs.entity;

public class Service {
	public static final String TABLE_NAME = "Service";
	public static final String SERVICE_DESC = "service_desc";
	public static final String SERVICE_PRICE = "service_price";
	private String serviceDesc; // primary key
	private double servicePrice;
	
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public double getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(String servicePrice) {
		this.servicePrice = Double.parseDouble(servicePrice);
	}
	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}
}
