package com.edu.mum.hbs.entity;

public class Service implements Entity{
	public static final String TABLE_NAME = "Service";
	public static final String SERVICE_DESC = "service_desc";
	public static final String SERVICE_PRICE = "service_price";
	@Id
	private String service_desc; // primary key
	@Column
	private Double service_price;
	
	public String getService_desc() {
		return service_desc;
	}
	public void setService_desc(String serviceDesc) {
		this.service_desc = serviceDesc;
	}
	public double getService_price() {
		return service_price;
	}
	public void setServicePriceByString(String servicePrice) {
		this.service_price = Double.parseDouble(servicePrice);
	}
	public void setService_price(Double servicePrice) {
		this.service_price = servicePrice;
	}
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
}
