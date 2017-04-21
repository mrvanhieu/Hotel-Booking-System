package com.edu.mum.hbs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustRoomDetails extends CustomerAndRoom {
	private Customer customer;
	private Room room;

	public CustRoomDetails(){}
	public CustRoomDetails(Customer customer, Room room) {
		super();
		this.customer = customer;
		this.room = room;
	}

	@JsonIgnoreProperties
	public String getFullName(){
		return customer.getFullName();
	}

	@Override
	@JsonIgnoreProperties
	public String getPassportOrId() {
		return customer.getPassportOrId();
	}

	@JsonIgnoreProperties
	public String getPhoneNo() {
		return customer.getPhoneNo();
	}

	@JsonIgnoreProperties
	public double getRoomPrice() {
		return room.getRoomPrice();
	}

	@JsonIgnoreProperties
	public String getRoomType() {
		return room.getRoomType();
	}

	@JsonIgnoreProperties
	public String getRoomClass() {
		return room.getRoomClass();
	}

	@Override
	@JsonIgnoreProperties
	public String getRoomNumber() {
		return room.getRoomNumber();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
