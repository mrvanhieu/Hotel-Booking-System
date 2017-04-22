package com.edu.mum.hbs.entity;

import java.time.LocalDate;

import com.edu.mum.hbs.dao.DaoFactoryImpl;
import com.edu.mum.hbs.dao.ServiceDao;
import com.edu.mum.hbs.restapi.util.LocalDateWithStringsDeserializer;
import com.edu.mum.hbs.restapi.util.LocalDateWithStringsSerializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class RoomService {
	public static final String TABLE_NAME = "RoomService";
	public static final String ROOM_NUMBER = "room_number";
	public static final String SERVICE_DESC = "service_desc";
	public static final String QUANTITY = "quantity";
	public static final String SERVICE_DATE = "service_date";
	private String roomNumber;
	private String serviceDesc;
	private int quantity;
	@JsonSerialize(using = LocalDateWithStringsSerializable.class)
	@JsonDeserialize(using = LocalDateWithStringsDeserializer.class)
	private LocalDate serviceDate;
	
	public LocalDate getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(LocalDate serviceDate) {
		this.serviceDate = serviceDate;
	}
	public void setServiceDateByString(String stringDate) {
		this.serviceDate = LocalDate.parse(stringDate);
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantityByString(String quantity) {
		this.quantity = Integer.parseInt(quantity);
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@JsonIgnore
	public double getServiceAmount(){
		return getServicePrice() * getQuantity();
	}

	@JsonIgnore
	public double getServicePrice(){
		ServiceDao serviceDao = (ServiceDao) DaoFactoryImpl.getFactory().createDao(Service.TABLE_NAME);
		Service service = serviceDao.getService(this.serviceDesc);
		return service.getServicePrice();
	}
}
