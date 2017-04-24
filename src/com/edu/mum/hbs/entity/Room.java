package com.edu.mum.hbs.entity;

public class Room  implements Entity, Prototype{
	public static final String TABLE_NAME = "Room";
	@Id
	protected String room_number;
	
	@Column
	protected String room_type;
	
	@Column
	protected String room_class;
	
	@Column
	protected Double price;
	
	public String getRoom_number() {
		return room_number;
	}
	public void setRoom_number(String roomNumber) {
		this.room_number = roomNumber;
	}
	public Double getPrice() {
		return price;
	}
//	public String getRoomPriceDollar() {
//		return "$" + roomPrice;
//	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setRoomPriceByString(String price) {
		this.price = Double.parseDouble(price);
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String roomType) {
		this.room_type = roomType;
	}
	public String getRoom_class() {
		return room_class;
	}
	public void setRoom_class(String roomClass) {
		this.room_class = roomClass;
	}

	@Override
	public Prototype doClone() {
		Room room = new Room();
		room.setRoom_class(room_class);
		room.setRoom_number(room_number);
		room.setPrice(price);
		room.setRoom_type(room_type);

		return room;
	}
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}
}
