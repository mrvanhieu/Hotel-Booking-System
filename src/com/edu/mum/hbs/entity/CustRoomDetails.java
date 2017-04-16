package com.edu.mum.hbs.entity;

public class CustRoomDetails extends CustomerAndRoom {
	private Customer customer;
	private Room room;
	
	public CustRoomDetails(Customer customer, Room room) {
		super();
		this.customer = customer;
		this.room = room;
		
	}
	
	public String getFullName() {
		return customer.getFullName();
	}

	@Override
	public String getPassportOrId() {
		return customer.getPassportOrId();
	}
	
	public String getPhoneNo() {
		return customer.getPhoneNo();
	}
	
	public double getRoomPrice() {
		return room.getRoomPrice();
	}
	
	public String getRoomType() {
		return room.getRoomType();
	}

	public String getRoomClass() {
		return room.getRoomClass();
	}

	@Override
	public String getRoomNumber() {
		return room.getRoomNumber();
	}
	
	
}
