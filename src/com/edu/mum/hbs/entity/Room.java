package com.edu.mum.hbs.entity;

public class Room implements Prototype{
	public static final String TABLE_NAME = "Room";
	protected String roomNumber;
	protected String roomType;
	protected String roomClass;
	protected double roomPrice;
	
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public double getRoomPrice() {
		return roomPrice;
	}
	public String getRoomPriceDollar() {
		return "$" + roomPrice;
	}
	public void setRoomPrice(double price) {
		this.roomPrice = price;
	}
	public void setRoomPrice(String price) {
		this.roomPrice = Double.parseDouble(price);
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomClass() {
		return roomClass;
	}
	public void setRoomClass(String roomClass) {
		this.roomClass = roomClass;
	}

	@Override
	public Prototype doClone() {
		Room room = new Room();
		room.setRoomClass(roomClass);
		room.setRoomNumber(roomNumber);
		room.setRoomPrice(roomPrice);
		room.setRoomType(roomType);

		return room;
	}
}
